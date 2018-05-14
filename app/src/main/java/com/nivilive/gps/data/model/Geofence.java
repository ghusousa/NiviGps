package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

public class Geofence {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	public final int getId() {
		return this.id;
	}

	public final String getName() {
		return this.name;
	}

	public final String getDescription() {
		return this.description;
	}

}