package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

public class RegisteredUser {

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("password")
	private String password;

	public RegisteredUser(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
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