package com.nivilive.gps.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nivilive.gps.data.db.AppDatabase;
import com.nivilive.gps.data.db.dao.EventDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

	@Provides
	@Singleton
	@NonNull
	public AppDatabase provideDatabase(@NonNull Context context) {
		return Room.databaseBuilder(context, AppDatabase.class, "nivi.db").build();
	}

	@Provides
	@Singleton
	public EventDao provideEventDao(@NonNull AppDatabase database) {
		return database.eventDao();
	}

}