package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class Driver {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("uniqueId")
	private String uniqueId;

	@SerializedName("attributes")
	private LinkedHashMap<String, String> attributes;

	public final int getId() {
		return this.id;
	}

	public final String getName() {
		return this.name;
	}

	public final String getUniqueId() {
		return this.uniqueId;
	}

	public final LinkedHashMap<String, String> getAttributes() {
		return this.attributes;
	}

}