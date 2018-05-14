package com.nivilive.gps.adapter.delegate;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nivilive.gps.adapter.item.Item;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

public abstract class BaseAdapterDelegate<T extends Item, VH extends RecyclerView.ViewHolder> extends AdapterDelegate<List<Item>> {

	protected final Context context;
	private final LayoutInflater inflater;

	public BaseAdapterDelegate(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	protected boolean isForViewType(@NonNull List<Item> items, int position) {
		return isForViewType(items.get(position));
	}

	protected abstract Boolean isForViewType(Item item);

	@LayoutRes
	protected abstract int getLayoutId();

	protected abstract VH createViewHolder(View view);

	protected abstract void onBind(T item, VH holder);

	@NonNull
	@Override
	protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
		View view = inflater.inflate(getLayoutId(), parent, false);
		return createViewHolder(view);
	}

	@Override
	protected void onBindViewHolder(@NonNull List<Item> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
		T item = (T) items.get(position);
		onBind(item, (VH) holder);
	}

}