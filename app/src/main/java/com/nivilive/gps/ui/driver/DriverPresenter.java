package com.nivilive.gps.ui.driver;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.data.model.Driver;
import com.nivilive.gps.mvp.BasePresenter;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.driver.item.DriverAttributeItem;
import com.nivilive.gps.ui.driver.item.DriverNameItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.terrakok.cicerone.Router;


@InjectViewState
public class DriverPresenter extends BasePresenter<DriverView> {

	@NonNull
	private final SharedPreferences prefs;
	@NonNull
	private final SchedulersFactory schedulers;
	@NonNull
	private final RestApi api;
	@NonNull
	private final Router router;

	public DriverPresenter(@NonNull SharedPreferences prefs, @NonNull SchedulersFactory schedulers, @NonNull RestApi api, @NonNull Router router) {
		this.prefs = prefs;
		this.schedulers = schedulers;
		this.api = api;
		this.router = router;
	}

	@Override
	protected void onFirstViewAttach() {
		getViewState().showLoading();
		String token = prefs.getString(Prefs.PREF_KEY_AUTHORIZATION, "");
		unsubscribeOnDestroy(api.getDrivers(token)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(it -> {
						getViewState().hideLoading();
						getViewState().displayItems(prepareDriverItems(it));
				}, it -> {
						getViewState().hideLoading();
						getViewState().showError("Error getting information about the drivers");
				})
		);
	}

	private List<Item> prepareDriverItems(List<Driver> drivers) {
		List<Item> driverItems = new ArrayList<>();
		for (Driver driver : drivers) {
			driverItems.add(new DriverNameItem(driver.getName()));
			for (Map.Entry<String, String> it : driver.getAttributes().entrySet()) {
				driverItems.add(new DriverAttributeItem(it.getKey(), it.getValue()));
			}
		}
		return driverItems;
	}

	public void onUpClick() {
		router.exit();
	}

}