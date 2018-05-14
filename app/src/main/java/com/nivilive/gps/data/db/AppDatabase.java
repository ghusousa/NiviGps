package com.nivilive.gps.data.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.nivilive.gps.data.db.converter.Converters;
import com.nivilive.gps.data.db.dao.EventDao;
import com.nivilive.gps.data.db.entity.EventEntity;

@Database(entities = EventEntity.class, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

	public abstract EventDao eventDao();

}