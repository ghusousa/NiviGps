package com.nivilive.gps.app;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.nivilive.gps.BuildConfig;
import com.nivilive.gps.di.DaggerAppComponent;
import com.nivilive.gps.service.NotificationJobService;

import java.util.concurrent.TimeUnit;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class TrackerApplication extends DaggerApplication {

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent.builder().create(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		startJob();
	}

	private void startJob() {
		FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getBaseContext()));
		int periodicity = (int) TimeUnit.MINUTES.toSeconds(5); // every 15 minutes periodicity expressed as seconds
		int toleranceInterval = (int) TimeUnit.MINUTES.toSeconds(1); // a small(ish) window of time when triggering is OK
		Job job = dispatcher.newJobBuilder()
			.setTag("JOB")
			.setService(NotificationJobService.class)
            .setTrigger(Trigger.executionWindow(periodicity, periodicity + toleranceInterval))
			.setLifetime(Lifetime.FOREVER)
			.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
			.setRecurring(true)
			.setReplaceCurrent(true)
			.build();
		dispatcher.schedule(job);
	}

}