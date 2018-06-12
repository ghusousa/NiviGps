package com.nivilive.gps.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nivilive.gps.R;
import com.nivilive.gps.data.db.DatabaseHandler;
import com.nivilive.gps.data.model.ItemNotification;
import com.nivilive.gps.ui.Notifications;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.nivilive.gps.data.Prefs.PREF_KEY_NOTIFICATION;
import static com.nivilive.gps.data.Prefs.PREF_KEY_SOUND;
import static com.nivilive.gps.data.Prefs.PREF_KEY_VIBRATE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                String event_id = json.optString("event_id");
                String date = json.optString("date");
                String status = json.optString("status");
                String address = json.optString("address");
                Log.d("Got Notification", "event_id : " + event_id + " -- msg : " + address);
                ItemNotification notification = new ItemNotification();
                notification.setEventId(event_id);
                notification.setStatus(status);
                notification.setDate(date);
                notification.setAddress(address);

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                String node_id = db.saveNotification(notification);
                Log.d("node_id", "Notification Saved : " + node_id);
                if (SharedPrefManager.getBoolPreferences(getApplicationContext(), PREF_KEY_NOTIFICATION)) {
                    showNotification(R.drawable.logo, event_id + " was " + status + " at " + address + " on " + date, node_id);
                } else {
                    Log.d("notification", "Notification is disabled in device");
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void showNotification(int icon, String message, String cs_id) {
        Intent intent = new Intent(getApplicationContext(), Notifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), Integer.parseInt(cs_id), intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent);
        if (SharedPrefManager.getBoolPreferences(getApplicationContext(), PREF_KEY_SOUND)) {
            notificationBuilder.setSound(defaultSoundUri);
        }
        if (SharedPrefManager.getBoolPreferences(getApplicationContext(), PREF_KEY_VIBRATE)) {
            notificationBuilder.setVibrate(new long[]{500, 500, 500, 500});
        }
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(cs_id), notificationBuilder.build());
    }

}