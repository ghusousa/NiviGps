package com.nivilive.gps.ui.tracking;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nivilive.gps.R;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public  class TrackingFragment extends BaseFragment
		implements TrackingView, OnMapReadyCallback {

	public static final Double DEFAULT_LATITUDE = 28.668368;
	public static final Double DEFAULT_LONGITUDE = 77.122060;

	@Inject
	SchedulersFactory schedulers;
	@Inject
	Router router;
	@Inject
	TrackingInteractor interactor;
	@Inject
	SharedPreferences prefs;
	@InjectPresenter
	TrackingPresenter presenter;

	@ProvidePresenter
	public TrackingPresenter providePresenter() {
		return new TrackingPresenter(schedulers, interactor, router, prefs);
	}

	private SupportMapFragment mapFragment;
	private GoogleMap map;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_tracking;
	}

	@Override
	protected void initControls(@NonNull View v) {
		mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap map) {
		this.map = map;
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setMapToolbarEnabled(false);
		map.getUiSettings().setMyLocationButtonEnabled(true);
		presenter.onMapInitialized();
	}



	@Override
	public void displayPosition(@NotNull PositionViewItem position, @NonNull String positionInfo) {
		map.addMarker(new MarkerOptions()
				.position(new LatLng(position.getLatitude(), position.getLongitude()))
				.title(position.getName())
				.snippet(positionInfo)
		//		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus))
				.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car))
		);

	}

	@Override
	public void moveCamera(@NonNull LatLngBounds bounds) {
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 12.0f/*getResources().getDimensionPixelSize(R.dimen.map_padding)*/);
		map.animateCamera(cu);
	}

	@Override
	public void displayConfirmExitDialog() {
		new AlertDialog.Builder(getActivity())
				.setMessage(R.string.tracking_dialog_logout_confirm)
				.setPositiveButton(R.string.button_logout, ((dialog, which) -> presenter.onLogoutConfirmClick()))
				.setNegativeButton(R.string.button_cancel, null)
				.show();
	}

	@Override
	public void displayError(@NonNull String message) {
		Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void clearPositions() {
		map.clear();
	}

	@Override
	public void initZoom() {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE), 12.0f));
	}

}