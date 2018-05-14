package com.nivilive.gps.ui.driver.delegate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nivilive.gps.R;
import com.nivilive.gps.adapter.delegate.BaseAdapterDelegate;
import com.nivilive.gps.adapter.item.Item;
import com.nivilive.gps.ui.driver.item.DriverAttributeItem;

public class DriverAttributeDelegate extends BaseAdapterDelegate<DriverAttributeItem, DriverAttributeDelegate.DriverAttributeHolder> {

	public DriverAttributeDelegate(Context context) {
		super(context);
	}

	@Override
	protected Boolean isForViewType(Item item) {
		return item instanceof DriverAttributeItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_driver_attribute;
	}

	@Override
	protected DriverAttributeHolder createViewHolder(View view) {
		return new DriverAttributeHolder(view);
	}

	@Override
	protected void onBind(DriverAttributeItem item, DriverAttributeHolder holder) {
		holder.textLabel.setText(item.getLabel());
		holder.textValue.setText(item.getText());
	}

	class DriverAttributeHolder extends RecyclerView.ViewHolder {

		TextView textLabel;
		TextView textValue;

		public DriverAttributeHolder(View itemView) {
			super(itemView);
			textLabel = itemView.findViewById(R.id.text_label);
			textValue = itemView.findViewById(R.id.text_value);
		}

	}

}