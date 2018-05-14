package com.nivilive.gps.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class NavigationModule {

	@Provides
	@Singleton
	public Cicerone<Router> provideCicerone() {
		return Cicerone.create();
	}

	@Provides
	@Singleton
	public Router provideRouter(Cicerone<Router> cicerone) {
		return cicerone.getRouter();
	}

	@Provides
	@Singleton
	public NavigatorHolder provideNavigatorHolder(Cicerone<Router> cicerone) {
		return cicerone.getNavigatorHolder();
	}

}