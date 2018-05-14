package com.nivilive.gps.ui.notifications.item;

import android.support.annotation.NonNull;

import com.nivilive.gps.adapter.item.Item;


public class EventViewItem implements Item {

	@NonNull
	private final String content;


	public EventViewItem(@NonNull String content) {
		this.content = content;
	}

	@NonNull
	public String getContent() {
		return content;
	}

}