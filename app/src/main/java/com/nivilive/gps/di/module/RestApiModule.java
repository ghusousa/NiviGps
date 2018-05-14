package com.nivilive.gps.di.module;

import com.nivilive.gps.data.api.RestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = RetrofitModule.class)
public class RestApiModule {

	@Provides
	@Singleton
	public RestApi provideRestApi(Retrofit retrofit) {
		return retrofit.create(RestApi.class);
	}

}