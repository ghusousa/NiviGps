package com.nivilive.gps.di.module;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = ContextModule.class)
public class HttpClientModule {

	private static final long CONNECT_TIMEOUT = 5L;
	private static final long READ_TIMEOUT = 5L;
	private static final long WRITE_TIMEOUT = 5L;

	@Provides
	@Singleton
	public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logInterceptor, Interceptor headerInterceptor) {
		return new OkHttpClient().newBuilder()
				.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
				.readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.MINUTES)
				.addInterceptor(logInterceptor)
				.addInterceptor(headerInterceptor)
				.build();
	}

	@Provides
	@Singleton
	public Interceptor provideHeaderInterceptor() {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request request = chain.request()
						.newBuilder()
						.addHeader("Accept", "application/json")
						.build();
				return chain.proceed(request);
			}
		};
	}

	@Provides
	@Singleton
	public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return interceptor;
	}

}
