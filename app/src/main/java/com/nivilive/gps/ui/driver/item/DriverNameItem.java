package com.nivilive.gps.ui.driver.item;

import android.support.annotation.NonNull;

import com.nivilive.gps.adapter.item.Item;

public class DriverNameItem implements Item {

	@NonNull
	private final String name;

	public DriverNameItem(@NonNull String name) {
		this.name = name;
	}

	@NonNull
	public String getName() {
		return name;
	}

}