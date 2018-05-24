package com.nivilive.gps.ui.tracking;

import android.support.annotation.NonNull;

import java.util.Date;

public final class PositionViewItem {
	@NonNull
	private final String name;
	@NonNull
	private final Date time;
	private final double speed;
	@NonNull
	private final String uniqueId;
	@NonNull
	private final String status;
	private final double latitude;
	private final double longitude;
	private final double course;
	private final boolean motion;

	@NonNull
	public final String getName() {
		return this.name;
	}

	@NonNull
	public final Date getTime() {
		return this.time;
	}

	public final double getSpeed() {
		return this.speed;
	}

	@NonNull
	public final String getUniqueId() {
		return this.uniqueId;
	}

	@NonNull
	public final String getStatus() {
		return this.status;
	}

	public final double getLatitude() {
		return this.latitude;
	}

	public final double getLongitude() {
		return this.longitude;
	}

	public PositionViewItem(@NonNull String name, @NonNull Date time, double speed, @NonNull String uniqueId, @NonNull String status, double latitude, double longitude, double course, boolean motion) {
		this.name = name;
		this.time = time;
		this.speed = speed;
		this.uniqueId = uniqueId;
		this.status = status;
		this.latitude = latitude;
		this.longitude = longitude;
		this.course = course;
		this.motion =motion;
	}

	public final double getCourse(){
		return this.course;
	}

	public final boolean getMotion(){
		return this.motion;
	}

}