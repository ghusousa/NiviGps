package com.nivilive.gps.ui.tracking;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLngBounds;


public interface TrackingView extends MvpView {

	void displayPosition(@NonNull PositionViewItem position, @NonNull String positionInfo);

	@StateStrategyType(SkipStrategy.class)
	void moveCamera(@NonNull LatLngBounds bounds);

	@StateStrategyType(SkipStrategy.class)
	void displayConfirmExitDialog();

	@StateStrategyType(SkipStrategy.class)
	void displayError(@NonNull String message);

	void clearPositions();

	void initZoom();

}