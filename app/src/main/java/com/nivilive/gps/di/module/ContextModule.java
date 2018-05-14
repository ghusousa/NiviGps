package com.nivilive.gps.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nivilive.gps.app.TrackerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

	@Provides
	@Singleton
	@NonNull
	public Context provideContext(@NonNull TrackerApplication application) {
		return application.getApplicationContext();
	}

}