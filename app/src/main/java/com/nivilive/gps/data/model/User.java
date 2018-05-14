package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("password")
	private String password;

	public final int getId() {
		return this.id;
	}

	public final String getName() {
		return this.name;
	}

	public final String getEmail() {
		return this.email;
	}

	public final String getPassword() {
		return this.password;
	}

}