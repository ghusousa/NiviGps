package com.nivilive.gps.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.nivilive.gps.R;
import com.nivilive.gps.adapter.base.DefaultAdapter;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.data.db.dao.EventDao;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.base.BaseFragment;
import com.nivilive.gps.ui.notifications.delegate.EventDelegate;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


public class NotificationsFragment extends BaseFragment implements NotificationsView {

	@Inject
	EventDao eventDao;
	@Inject
	SchedulersFactory schedulers;
	@Inject
	Router router;
	@Inject
	SharedPreferences prefs;
	@InjectPresenter
	NotificationsPresenter presenter;

	@ProvidePresenter
	public NotificationsPresenter providePresenter() {
		return new NotificationsPresenter(eventDao, schedulers, router, isOnline());
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			return netInfo != null && netInfo.isConnectedOrConnecting();
		}
		return false;
	}

	private RecyclerView recycler;
	private ProgressBar progress;
	private TextView empty;

	private DefaultAdapter adapter;
	private MultiSelector multiSelector = new MultiSelector();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_notifications;
	}

	@Override
	protected void initControls(@NonNull View v) {
		recycler = v.findViewById(R.id.recycler);
		progress = v.findViewById(R.id.progress);
		empty = v.findViewById(R.id.text_empty);
		initRecycler();
		initAdapter();
		multiSelector.setSelectable(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_notifications, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_refresh:
				presenter.refreshEvents();
				break;
			case R.id.action_delete:
				presenter.removeSelectedItems(multiSelector.getSelectedPositions());
				break;
		}
		return true;
	}

	//	private void initToolbar() {
//		toolbar.setTitle(R.string.notifications_title);
//		toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
//		toolbar.setNavigationOnClickListener(v -> presenter.onUpClick());
//		toolbar.inflateMenu(R.menu.menu_notifications);
//		toolbar.setOnMenuItemClickListener(item -> {
//			switch (item.getItemId()) {
//				case R.id.action_refresh:
//					presenter.refreshEvents();
//					break;
//				case R.id.action_delete:
//					presenter.removeSelectedItems(multiSelector.getSelectedPositions());
//					break;
//			}
//			return true;
//		});
//	}

	private void initRecycler() {
		recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		recycler.setHasFixedSize(true);
	}

	private void initAdapter() {
		adapter = new DefaultAdapter()
				.addDelegate(new EventDelegate(getActivity(), multiSelector));
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
	public void showMessage(@NonNull String message) {
		Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void displayItems(@NonNull List<Item> items) {
		multiSelector.clearSelections();
		adapter.clear();
		adapter.addItems(items);
		adapter.notifyDataSetChanged();
		empty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
	}

	@Override
	public void displayConfirmExitDialog() {
		new AlertDialog.Builder(getActivity())
				.setMessage(R.string.notifications_clear_confirm)
				.setPositiveButton(R.string.button_clear, ((dialog, which) -> presenter.removeSelectedItemsConfirm(multiSelector.getSelectedPositions())))
				.setNegativeButton(R.string.button_cancel, null)
				.show();
	}
}