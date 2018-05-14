package com.nivilive.gps.ui.tracking;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.data.model.Device;
import com.nivilive.gps.data.model.Position;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TrackingInteractor {

	@NonNull
	private final RestApi api;
	@NonNull
	private final SharedPreferences prefs;

	public TrackingInteractor(@NonNull RestApi api, @NonNull SharedPreferences prefs) {
		this.api = api;
		this.prefs = prefs;
	}

	public Observable<List<PositionViewItem>> getFullPositions() {
		String token = prefs.getString(Prefs.PREF_KEY_AUTHORIZATION, "");
		return Observable.zip(api.getPositions(token), api.getDevices(token),
				this::createPositionViewItems);
	}

	private List<PositionViewItem> createPositionViewItems(List<Position> positions, List<Device> devices) {
		List<PositionViewItem> positionViews = new ArrayList<>();
		for (Position position : positions) {
			for (Device device : devices) {
				if (position.getDeviceId() == device.getId()) {
					positionViews.add(new PositionViewItem(
							device.getName(),
							device.getLastUpdate(),
							position.getSpeed(),
							device.getUniqueId(),
							device.getStatus(),
							position.getLatitude(),
							position.getLongitude()
					));
				}
			}
		}
		return positionViews;
	}

}