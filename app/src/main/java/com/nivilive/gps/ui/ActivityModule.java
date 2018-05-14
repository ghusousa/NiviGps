package com.nivilive.gps.ui;

import com.nivilive.gps.di.annotation.FragmentScope;
import com.nivilive.gps.ui.driver.DriverFragment;
import com.nivilive.gps.ui.notifications.NotificationsFragment;
import com.nivilive.gps.ui.registration.RegistrationFragment;
import com.nivilive.gps.ui.signin.SignInFragment;
import com.nivilive.gps.ui.tracking.TrackingFragment;
import com.nivilive.gps.ui.tracking.TrackingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

	@ContributesAndroidInjector
	@FragmentScope
	abstract SignInFragment signInFragment();

	@ContributesAndroidInjector(modules = {TrackingModule.class})
	@FragmentScope
	abstract TrackingFragment trackingFragment();

	@ContributesAndroidInjector
	@FragmentScope
	abstract NotificationsFragment notificationsFragment();

	@ContributesAndroidInjector
	@FragmentScope
	abstract DriverFragment driverFragment();

	@ContributesAndroidInjector
	@FragmentScope
	abstract RegistrationFragment profileFragment();

}