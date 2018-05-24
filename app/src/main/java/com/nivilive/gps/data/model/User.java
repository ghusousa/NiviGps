package com.nivilive.gps.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("password")
	private String password;

	@SerializedName("phone")
    private String phone;

	@SerializedName("readOnly")
    private  boolean readOnly;

	@SerializedName("admin")
    private boolean admin;

	@SerializedName("map")
    private String map;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("zoom")
    private int zoom;

    @SerializedName("twelveHourFormat")
    private boolean twleveHourFormat;

    @SerializedName("expirationTime")
    private Date expirationTime;

    @SerializedName("coordinateFormat")
    private String coordinateFormat;

    @SerializedName("disabled")
    private boolean disabled;

    @SerializedName("deviceLimit")
    private int deviceLimit;

    @SerializedName("userLimit")
    private int userLimit;

    @SerializedName("deviceReadonly")
    private boolean deviceReadonly;

    @SerializedName("token")
    private String token;

    @SerializedName("limitCommands")
    private boolean limitCommands;

    @SerializedName("poiLayer")
    private String poiLayer;

	@SerializedName("attributes")
	private User.Attributes attributes;

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

    public final String getPhone(){
        return this.phone;
    }

    public final boolean isReadOnly(){
        return readOnly;
    }

    public final boolean isAdmin(){
        return admin;
    }

	public User.Attributes getAttributes(){
		return this.attributes;
	}

	public static class Attributes{

		@SerializedName("speedUnit")
		private String speedUnit;

		public String getSpeedUnit() {
			return this.speedUnit;
		}
	}

}