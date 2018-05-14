package com.nivilive.gps.ui.driver.delegate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nivilive.gps.R;
import com.nivilive.gps.adapter.delegate.BaseAdapterDelegate;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.ui.driver.item.DriverNameItem;

public class DriverNameDelegate extends BaseAdapterDelegate<DriverNameItem, DriverNameDelegate.DriverNameHolder> {

	public DriverNameDelegate(Context context) {
		super(context);
	}

	@Override
	protected Boolean isForViewType(Item item) {
		return item instanceof DriverNameItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_driver_name;
	}

	@Override
	protected DriverNameHolder createViewHolder(View view) {
		return new DriverNameHolder(view);
	}

	@Override
	protected void onBind(DriverNameItem item, DriverNameHolder holder) {
		holder.title.setText(item.getName());
	}

	class DriverNameHolder extends RecyclerView.ViewHolder {

		TextView title;

		public DriverNameHolder(View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.text_title);
		}

	}

}