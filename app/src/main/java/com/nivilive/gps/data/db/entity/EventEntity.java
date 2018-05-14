package com.nivilive.gps.data.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "events")
public class EventEntity {

	public EventEntity(int id, Date date, String content) {
		this.id = id;
		this.date = date;
		this.content = content;
	}

	@PrimaryKey
	private int id;

	@ColumnInfo(name = "date")
	private Date date;

	@ColumnInfo(name = "content")
	private String content;

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}
}