package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {

	public static final String EVENT_TYPE_ENTER = "geofenceEnter";
	public static final String EVENT_TYPE_EXIT = "geofenceExit";

	@SerializedName("id")
	private int id;

	@SerializedName("deviceId")
	private int deviceId;

	@SerializedName("type")
	private String type;

	@SerializedName("serverTime")
	private Date serverTime;

	@SerializedName("positionId")
	private int positionId;

	@SerializedName("geofenceId")
	private int geofenceId;

	public final int getId() {
		return this.id;
	}

	public final int getDeviceId() {
		return this.deviceId;
	}

	public final String getType() {
		return this.type;
	}

	public final Date getServerTime() {
		return this.serverTime;
	}

	public final int getPositionId() {
		return this.positionId;
	}

	public final int getGeofenceId() {
		return this.geofenceId;
	}

}