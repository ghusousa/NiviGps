package com.nivilive.gps.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class PreferencesModule {

	@Provides
	@Singleton
	public SharedPreferences provideSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

}