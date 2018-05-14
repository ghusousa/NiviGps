package com.nivilive.gps.di;

import com.nivilive.gps.app.TrackerApplication;
import com.nivilive.gps.di.module.AppModule;
import com.nivilive.gps.di.module.ContextModule;
import com.nivilive.gps.di.module.DatabaseModule;
import com.nivilive.gps.di.module.GsonModule;
import com.nivilive.gps.di.module.HttpClientModule;
import com.nivilive.gps.di.module.NavigationModule;
import com.nivilive.gps.di.module.PreferencesModule;
import com.nivilive.gps.di.module.RestApiModule;
import com.nivilive.gps.di.module.RetrofitModule;
import com.nivilive.gps.di.module.SchedulersModule;
import com.nivilive.gps.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
		AppModule.class,
		DatabaseModule.class,
		NavigationModule.class,
		ContextModule.class,
		PreferencesModule.class,
		HttpClientModule.class,
		GsonModule.class,
		RetrofitModule.class,
		RestApiModule.class,
		SchedulersModule.class,
		AndroidSupportInjectionModule.class,
		ServiceModule.class
})
public interface AppComponent extends AndroidInjector<TrackerApplication> {

	@Component.Builder
	abstract class Builder extends AndroidInjector.Builder<TrackerApplication> {

	}

}
