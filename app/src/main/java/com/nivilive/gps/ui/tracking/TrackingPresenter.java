package com.nivilive.gps.ui.tracking;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.mvp.BasePresenter;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.Screens;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TrackingPresenter extends BasePresenter<TrackingView> {

	public static final long REFRESH_PERIOD = 5L; // seconds
	private DateFormat engDateFormat = new SimpleDateFormat("dd.MM.yyyy h:mm:ss a", Locale.ENGLISH);
	private DecimalFormat decimalFormat = new DecimalFormat("###.##");

	@NonNull
	private final SchedulersFactory schedulers;
	@NonNull
	private final TrackingInteractor interactor;
	@NonNull
	private final Router router;
	@NonNull
	private final SharedPreferences prefs;

	public TrackingPresenter(@NonNull SchedulersFactory schedulers,
	                         @NonNull TrackingInteractor interactor,
	                         @NonNull Router router,
	                         @NonNull SharedPreferences prefs) {
		this.schedulers = schedulers;
		this.interactor = interactor;
		this.router = router;
		this.prefs = prefs;
	}

	private boolean mapZoomPerformed = false;

	public void onMapInitialized() {
		getViewState().initZoom();
		getFullPositions();
		unsubscribeOnDestroy(Observable.interval(REFRESH_PERIOD, TimeUnit.SECONDS, schedulers.computation())
				.subscribe(it -> getFullPositions(), throwable -> {

				}));
	}

	private void getFullPositions() {
		unsubscribeOnDestroy(interactor.getFullPositions()
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(positions -> {
					getViewState().clearPositions();
					displayPositions(positions);
					if (!mapZoomPerformed) {
						mapZoomPerformed = true;
						performAutoZoom(positions);
					}
				}, throwable -> {

				}));
	}

	private void displayPositions(List<PositionViewItem> positions) {
		for (PositionViewItem position : positions) {
			String snippet = new StringBuilder()
                    .append("Last Update: ")
					.append(engDateFormat.format(position.getTime())).append('\n')
					.append(", speed: ")
					.append(decimalFormat.format(convertKnots2Kmph(position.getSpeed())))
					.append(" kmh").append('\n')
					.append(", Status:")
					.append(position.getStatus()).append('\n')
                    .append(", Motion:")
                    .append(position.getMotion())
					.toString();
			getViewState().displayPosition(position, snippet);
		}
	}

	private double convertKnots2Kmph(double speed) {
		return speed * 1.85;
	}

	private void performAutoZoom(List<PositionViewItem> positions) {
		if (!positions.isEmpty()) {
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			for (PositionViewItem position : positions) {
				builder.include(new LatLng(position.getLatitude(), position.getLongitude()));
			}
			getViewState().moveCamera(builder.build());
		}
	}

	public void onDriverClick() {
		router.navigateTo(Screens.DRIVER_FRAGMENT);
	}

	public void onNotificationsClick() {
		router.navigateTo(Screens.NOTIFICATIONS_FRAGMENT);
	}

	public void onLogoutClick() {
		getViewState().displayConfirmExitDialog();
	}

	public void onLogoutConfirmClick() {
		prefs.edit()
				.remove(Prefs.PREF_KEY_REMEMBER_ME)
				.remove(Prefs.PREF_KEY_AUTHORIZATION)
				.apply();
		router.replaceScreen(Screens.SIGN_IN_SCREEN);
	}

}