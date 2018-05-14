package com.nivilive.gps.rx;

import io.reactivex.Scheduler;

public interface SchedulersFactory {

	Scheduler io();
	Scheduler ui();
	Scheduler computation();

}