package com.nivilive.gps.di.module;

import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.rx.SchedulersFactoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulersModule {

	@Provides
	@Singleton
	public SchedulersFactory provideSchedulers() {
		return new SchedulersFactoryImpl();
	}

}