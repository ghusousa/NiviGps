package com.nivilive.gps.di.module;

import com.nivilive.gps.di.annotation.ServiceScope;
import com.nivilive.gps.service.NotificationJobService;
import com.nivilive.gps.service.ServiceLevelModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {

	@ContributesAndroidInjector(modules = ServiceLevelModule.class)
	@ServiceScope
	public abstract NotificationJobService notificationJobService();

}