package com.nivilive.gps.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nivilive.gps.adapter.item.Item;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

public class DefaultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private AdapterDelegatesManager<List<Item>> delegatesManager = new AdapterDelegatesManager<>();
	private List<Item> items = new ArrayList<>();

	@Override
	public int getItemViewType(int position) {
		return delegatesManager.getItemViewType(items, position);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return delegatesManager.onCreateViewHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		delegatesManager.onBindViewHolder(items, position, holder);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void clear() {
		items.clear();
	}

	public void addItems(List<Item> items) {
		this.items.addAll(items);
	}

	public Item getItem(int position) {
		if (position >=0 && position < items.size()) {
			return items.get(position);
		}
		return null;
	}

	public DefaultAdapter addDelegate(AdapterDelegate<List<Item>> delegate) {
		delegatesManager.addDelegate(delegate);
		return this;
	}

}