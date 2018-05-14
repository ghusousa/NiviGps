package com.nivilive.gps.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Device {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("uniqueId")
	private String uniqueId;

	@SerializedName("status")
	private String status;

	@SerializedName("lastUpdate")
	private Date lastUpdate;

	@SerializedName("positionId")
	private int positionId;

	@SerializedName("groupId")
	private int groupId;

	@SerializedName("geofenceIds")
	private List geofences;

	@SerializedName("phone")
	private String phone;

	@SerializedName("model")
	private String model;

	@SerializedName("category")
	private String category;

	public int getId() {
		return this.id;
	}

	@NonNull
	public String getName() {
		return this.name;
	}

	@NonNull
	public String getUniqueId() {
		return this.uniqueId;
	}

	@NonNull
	public String getStatus() {
		return this.status;
	}

	@NonNull
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public int getPositionId() {
		return this.positionId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	@NonNull
	public List getGeofences() {
		return this.geofences;
	}

	@NonNull
	public String getPhone() {
		return this.phone;
	}

	@NonNull
	public String getModel() {
		return this.model;
	}

	@NonNull
	public String getCategory() {
		return this.category;
	}

}