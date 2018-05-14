package com.nivilive.gps.data.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

	@TypeConverter
	public Date fromTimestamp(Long value) {
		return value == null ? null : new Date(value);
	}

	@TypeConverter
	public Long dateToTimestamp(Date date) {
		return date.getTime();
	}

}