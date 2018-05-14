package com.nivilive.gps.di.module;

import android.support.annotation.NonNull;

import com.nivilive.gps.di.annotation.ActivityScope;
import com.nivilive.gps.ui.ActivityModule;
import com.nivilive.gps.ui.MainActivity;
import com.nivilive.gps.ui.SignInActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppModule {

	@ContributesAndroidInjector(
			modules = {ActivityModule.class}
	)
	@ActivityScope
	@NonNull
	public abstract SignInActivity signInActivity();

	@ContributesAndroidInjector(
			modules = {ActivityModule.class}
	)
	@ActivityScope
	@NonNull
	public abstract MainActivity mainActivity();

}