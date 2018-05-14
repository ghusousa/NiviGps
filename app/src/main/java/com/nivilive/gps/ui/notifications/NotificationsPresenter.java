package com.nivilive.gps.ui.notifications;

import com.arellomobile.mvp.InjectViewState;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.data.db.dao.EventDao;
import com.nivilive.gps.data.db.entity.EventEntity;
import com.nivilive.gps.mvp.BasePresenter;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.notifications.item.EventViewItem;

import java.util.ArrayList;
import java.util.List;

import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NotificationsPresenter extends BasePresenter<NotificationsView> {

	private final EventDao eventDao;
	private final SchedulersFactory schedulers;
	private final Router router;
	private final boolean online;

	private List<EventEntity> loadedEvents = new ArrayList<>();

	public NotificationsPresenter(EventDao eventDao, SchedulersFactory schedulers, Router router, boolean online) {
		this.eventDao = eventDao;
		this.schedulers = schedulers;
		this.router = router;
		this.online = online;
	}

	@Override
	protected void onFirstViewAttach() {
		loadEvents();
	}

	private void loadEvents() {
		getViewState().showLoading();
		unsubscribeOnDestroy(eventDao.getEvents()
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(events -> {
					loadedEvents.clear();
					loadedEvents.addAll(events);
					getViewState().hideLoading();
					getViewState().displayItems(prepareItems(events));
					if (online) {
						getViewState().showMessage("Notifications successfully updated");
					} else {
						getViewState().showMessage("Internet error. Please, try again later.");
					}
				}, it -> {
					getViewState().hideLoading();
					getViewState().showMessage("Error getting notifications");
				}));
	}

	private List<Item> prepareItems(List<EventEntity> events) {
		List<Item> eventItems = new ArrayList<>();
		for (EventEntity event : events) {
			eventItems.add(new EventViewItem(event.getContent()));
		}
		return eventItems;
	}

	public void refreshEvents() {
		loadEvents();
	}

	public void removeSelectedItems(List<Integer> selectedPositions) {
		if (selectedPositions.isEmpty()) {
			getViewState().showMessage("Select at least one notification");
		} else {
			getViewState().displayConfirmExitDialog();
		}
	}

	public void removeSelectedItemsConfirm(List<Integer> selectedPositions) {
		try {
			getViewState().showLoading();
			schedulers.io().scheduleDirect(() -> {
				if (!selectedPositions.isEmpty()) {
					for (Integer index : selectedPositions) {
						EventEntity event = loadedEvents.get(index);
						eventDao.deleteEvent(event);
					}
				}
			});
		} catch (Exception e) {
			Timber.e(e);
		} finally {
			getViewState().hideLoading();
			loadEvents();
		}
	}

	public void onUpClick() {
		router.exit();
	}

}