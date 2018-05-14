package com.nivilive.gps.ui.driver;

import com.arellomobile.mvp.MvpView;
import com.nivilive.gps.adapter.item.Item;

import java.util.List;

public interface DriverView extends MvpView {

	void showLoading();
	void hideLoading();
	void showError(String message);
	void displayItems(List<Item> items);

}