package com.nivilive.gps.ui.driver;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.nivilive.gps.R;
import com.nivilive.gps.adapter.base.DefaultAdapter;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.base.BaseFragment;
import com.nivilive.gps.ui.driver.delegate.DriverAttributeDelegate;
import com.nivilive.gps.ui.driver.delegate.DriverNameDelegate;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


public class DriverFragment extends BaseFragment implements DriverView {

	@Inject
	SchedulersFactory schedulers;
	@Inject
	Router router;
	@Inject
	SharedPreferences prefs;
	@Inject
	RestApi api;
	@InjectPresenter
	DriverPresenter presenter;

	@ProvidePresenter
	DriverPresenter providePresenter() {
		return new DriverPresenter(prefs, schedulers, api, router);
	}

	private RecyclerView recycler;
	private ProgressBar progress;

	private DefaultAdapter adapter;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_driver;
	}

	@Override
	protected void initControls(@NonNull View v) {
		recycler = v.findViewById(R.id.recycler);
		progress = v.findViewById(R.id.progress);
		initRecycler();
		initAdapter();
	}

	private void initRecycler() {
		recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		recycler.setHasFixedSize(true);
	}

	private void initAdapter() {
		adapter = new DefaultAdapter()
				.addDelegate(new DriverNameDelegate(getActivity()))
				.addDelegate(new DriverAttributeDelegate(getActivity()));
		recycler.setAdapter(adapter);
	}


	@Override
	public void showLoading() {
		progress.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoading() {
		progress.setVisibility(View.GONE);
	}

	@Override
	public void showError(String message) {
		Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void displayItems(List<Item> items) {
		adapter.clear();
		adapter.addItems(items);
		adapter.notifyDataSetChanged();
	}
}
