package com.nivilive.gps.ui.notifications.delegate;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.nivilive.gps.R;
import com.nivilive.gps.adapter.delegate.BaseAdapterDelegate;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.ui.notifications.item.EventViewItem;


public class EventDelegate extends BaseAdapterDelegate<EventViewItem, EventDelegate.EventHolder> {

	private final MultiSelector multiSelector;

	public EventDelegate(Context context, MultiSelector multiSelector) {
		super(context);
		this.multiSelector = multiSelector;
	}

	@Override
	protected Boolean isForViewType(Item item) {
		return item instanceof EventViewItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_event_item;
	}

	@Override
	protected EventHolder createViewHolder(View view) {
		return new EventHolder(view);
	}

	@Override
	protected void onBind(EventViewItem item, EventHolder holder) {
		holder.textContent.setText(item.getContent());
	}

	class EventHolder extends SwappingHolder implements View.OnClickListener {

		TextView textContent;

		public EventHolder(View itemView) {
			super(itemView, multiSelector);
			itemView.setOnClickListener(this);
			textContent = itemView.findViewById(R.id.text_content);
		}

		@Override
		public void onClick(View v) {
			boolean isSelected = multiSelector.isSelected(getAdapterPosition(), 0);
			multiSelector.setSelected(this, !isSelected);
		}

	}

}