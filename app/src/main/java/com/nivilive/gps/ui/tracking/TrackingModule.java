package com.nivilive.gps.ui.tracking;

import android.content.SharedPreferences;

import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.di.annotation.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class TrackingModule {

	@FragmentScope
	@Provides
	TrackingInteractor provideInteractor(RestApi api, SharedPreferences prefs) {
		return new TrackingInteractor(api, prefs);
	}

}