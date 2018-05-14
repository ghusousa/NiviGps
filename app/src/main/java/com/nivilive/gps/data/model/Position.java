package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Position {
	
	@SerializedName("id")
	private int id;

	@SerializedName("attributes")
	private Position.Attributes attributes;

	@SerializedName("deviceId")
	private int deviceId;

	@SerializedName("type")
	private String type;

	@SerializedName("protocol")
	private String protocol;

	@SerializedName("serverTime")
	private Date serverTime;

	@SerializedName("deviceTime")
	private Date deviceTime;

	@SerializedName("fixTime")
	private Date fixTime;

	@SerializedName("outdated")
	private boolean outdated;

	@SerializedName("valid")
	private boolean valid;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("altitude")
	private double altitude;

	@SerializedName("speed")
	private double speed;

	@SerializedName("course")
	private double course;

	@SerializedName("address")
	private String address;

	@SerializedName("accuracy")
	private double accuracy;

	public int getId() {
		return this.id;
	}

	public Position.Attributes getAttributes() {
		return this.attributes;
	}

	public int getDeviceId() {
		return this.deviceId;
	}

	public String getType() {
		return this.type;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public Date getServerTime() {
		return this.serverTime;
	}

	public Date getDeviceTime() {
		return this.deviceTime;
	}

	public Date getFixTime() {
		return this.fixTime;
	}

	public boolean getOutdated() {
		return this.outdated;
	}

	public boolean getValid() {
		return this.valid;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public double getAltitude() {
		return this.altitude;
	}

	public double getSpeed() {
		return this.speed;
	}

	public double getCourse() {
		return this.course;
	}

	public String getAddress() {
		return this.address;
	}

	public double getAccuracy() {
		return this.accuracy;
	}

	public static class Attributes {

		@SerializedName("sat")
		private int sat;

		@SerializedName("distance")
		private double distance;

		@SerializedName("totalDistance")
		private double totalDistance;

		@SerializedName("motion")
		private boolean motion;

		public int getSat() {
			return this.sat;
		}

		public double getDistance() {
			return this.distance;
		}

		public double getTotalDistance() {
			return this.totalDistance;
		}

		public boolean getMotion() {
			return this.motion;
		}

	}
}

