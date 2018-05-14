package com.nivilive.gps.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersFactoryImpl implements SchedulersFactory {

	@Override
	public Scheduler io() {
		return Schedulers.io();
	}

	@Override
	public Scheduler ui() {
		return AndroidSchedulers.mainThread();
	}

	@Override
	public Scheduler computation() {
		return Schedulers.computation();
	}

}