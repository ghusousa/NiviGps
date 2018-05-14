package com.nivilive.gps.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.nivilive.gps.R;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.data.db.dao.EventDao;
import com.nivilive.gps.data.db.entity.EventEntity;
import com.nivilive.gps.data.model.Device;
import com.nivilive.gps.data.model.Event;
import com.nivilive.gps.data.model.Geofence;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import timber.log.Timber;

public class NotificationJobService extends JobService {

	public static final String SCHOOL = "school";
	public static final String PICKUP = "pick up";
	public static final int NOTIFICATION_ID = 1200;
	public static final String NOTIFICATION_CHANNEL = "nivi_channel";

	@Inject
	SharedPreferences prefs;
	@Inject
	SchedulersFactory schedulers;
	@Inject
	RestApi api;
	@Inject
	EventDao eventDao;
	private List<Geofence> geofences = new ArrayList<>();
	private SimpleDateFormat engDateFormat = new SimpleDateFormat("dd.MM.yyyy h:mm:ss a", Locale.ENGLISH);

	@Override
	public void onCreate() {
		AndroidInjection.inject(this);
		super.onCreate();
	}

	@Override
	public boolean onStartJob(JobParameters params) {
		final String token = prefs.getString(Prefs.PREF_KEY_AUTHORIZATION, "");
		if (!token.isEmpty()) {
			final Date currentSyncTime = new Date();
			final Date lastSyncTime = new Date(prefs.getLong(Prefs.PREF_KEY_LAST_SYNC_TIME, currentSyncTime.getTime() - 7 * 24 * 60 * 60 * 1000)); // 7 days
			final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
			Observable.zip(api.getDevices(token), api.getGeofences(token),
					(devices, geofences) -> saveDevicesPositions(devices, geofences))
					.subscribeOn(schedulers.io())
					.subscribe(devices -> {
						for (Device d : devices) {
							int deviceId = d.getId();
							// get notifications
							api.getEventsByDevice(token, deviceId, formatter.format(lastSyncTime), formatter.format(currentSyncTime))
									.subscribeOn(schedulers.io())
									.subscribe(events -> {
										for (Event event : events) {
											try {
												String eventContent = prepareContentForEvent(event);
												generateNotification(eventContent);
												EventEntity eventRecord = new EventEntity(
														event.getId(),
														event.getServerTime(),
														eventContent);
												eventDao.insertEvent(eventRecord);
											} catch (Exception e) {
												Timber.e(e);
											}
										}
									});
						}
						// save sync time
						prefs.edit()
								.putLong(Prefs.PREF_KEY_LAST_SYNC_TIME, currentSyncTime.getTime())
								.apply();
					}, throwable -> Timber.e(throwable, "Error getting events from server"));

		}

		return true;
	}

	private List<Device> saveDevicesPositions(List<Device> devices, List<Geofence> geofences) {
		this.geofences = geofences;
		return devices;
	}

	private String prepareContentForEvent(Event event) {
		StringBuilder sb = new StringBuilder();
		sb.append("Respected Sir/Madam,");
		sb.append("\n");
		Geofence geofence = getGeofence(event.getGeofenceId());
		if (geofence != null) {
			if (event.getType().equals(Event.EVENT_TYPE_ENTER) &&
					(geofence.getName().contains(SCHOOL) || geofence.getDescription().contains(SCHOOL))) {
				sb.append("Your child has reached School at");
				sb.append("\n");
			} else if (event.getType().equals(Event.EVENT_TYPE_EXIT) &&
					(geofence.getName().contains(SCHOOL) || geofence.getDescription().contains(SCHOOL))) {
				sb.append("Your child has departed from School at");
				sb.append("\n");
			} else if (event.getType().equals(Event.EVENT_TYPE_ENTER) && geofence.getName().contains(PICKUP)) {
				sb.append("Your child’s School bus will arrive shortly at ")
						.append(geofence.getName())
						.append(" ")
						.append(geofence.getDescription())
						.append(".");
				sb.append("\n");
			} else {

			}
		}
		sb.append(engDateFormat.format(event.getServerTime()));
		sb.append("\n");
		sb.append("Thanks,").append("\n");
		sb.append("School Administrator");
		return sb.toString();
	}

	private Geofence getGeofence(int geofenceId) {
		for (Geofence geofence : geofences) {
			if (geofence.getId() == geofenceId) {
				return geofence;
			}
		}
		return null;
	}

	private void generateNotification(String content) {
		boolean notificationOn = prefs.getBoolean(Prefs.PREF_KEY_NOTIFICATIONS_ON, true);
		if (notificationOn) {
			Intent resultIntent = new Intent(this, MainActivity.class);
			PendingIntent resultPendingIntent = PendingIntent.getActivity(
					this,
					0,
					resultIntent,
					PendingIntent.FLAG_UPDATE_CURRENT
			);
			NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
			bigText.bigText(content);
			bigText.setBigContentTitle("");
			bigText.setSummaryText(getString(R.string.app_name));

			NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
					.setSmallIcon(R.drawable.ic_bus)
					.setContentTitle("")
					.setContentText(content)
					.setStyle(bigText)
					.setContentIntent(resultPendingIntent)
					.setAutoCancel(true);

			NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				createNotificationChannel(notificationManager);
			}
			notificationManager.notify(NOTIFICATION_ID, builder.build());
		}
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private void createNotificationChannel(NotificationManager notificationManager) {
		NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL,
				"Events",
				NotificationManager.IMPORTANCE_HIGH);
		notificationManager.createNotificationChannel(channel);
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		Timber.d("onStopJob");
		return false;
	}

}