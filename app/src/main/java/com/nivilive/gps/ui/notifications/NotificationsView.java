package com.nivilive.gps.ui.notifications;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.nivilive.gps.adapter.item.Item;

import java.util.List;

public interface NotificationsView extends MvpView {

	void showLoading();

	void hideLoading();

	void showMessage(@NonNull String message);

	void displayItems(@NonNull List<Item> items);

	@StateStrategyType(SkipStrategy.class)
	void displayConfirmExitDialog();

}