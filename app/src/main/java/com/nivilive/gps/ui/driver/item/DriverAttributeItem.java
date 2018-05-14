package com.nivilive.gps.ui.driver.item;

import android.support.annotation.NonNull;

import com.nivilive.gps.adapter.item.Item;


public class DriverAttributeItem implements Item {

	@NonNull
	private final String label;
	@NonNull
	private final String text;


	public DriverAttributeItem(@NonNull String label, @NonNull String text) {
		this.label = label;
		this.text = text;
	}

	@NonNull
	public String getLabel() {
		return label;
	}

	@NonNull
	public String getText() {
		return text;
	}

}