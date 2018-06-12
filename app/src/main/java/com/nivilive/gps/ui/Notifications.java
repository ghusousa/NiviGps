package com.nivilive.gps.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivilive.gps.R;
import com.nivilive.gps.data.db.DatabaseHandler;
import com.nivilive.gps.data.model.ItemNotification;

import java.util.ArrayList;



public class Notifications extends AppCompatActivity {

    ArrayList<ItemNotification> world4;
    private RecyclerView recyclerView;
    private Adapter obj_adapter;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            DatabaseHandler db = new DatabaseHandler(Notifications.this);
            world4 = db.getAllNotifications();
            Log.d("world4Counts","count : "+world4.size());
            obj_adapter = new Adapter(world4);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(obj_adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("note_recieved"));
        DatabaseHandler db = new DatabaseHandler(Notifications.this);
        world4 = db.getAllNotifications();
        Log.d("world4Counts","count : "+world4.size());
        obj_adapter = new Adapter(world4);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(obj_adapter);

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        ArrayList<ItemNotification> world4;
        public Adapter(ArrayList<ItemNotification> world4) {
            this.world4 = world4;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            String eventId = world4.get(position).getEventId();
            String date = world4.get(position).getDate();
            String event = world4.get(position).getStatus();
            String address = world4.get(position).getAddress();
            holder.tvEventId.setText(eventId);
            holder.tvDate.setText(date);
            holder.tvEvent.setText(event);
            holder.tvAddress.setText(address);
            Log.d("seenStatus","seenStatus : "+eventId);
            if(event.equals("stopped")){
                holder.img_status.setImageDrawable(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_ev_stopped));
            }else if(event.equals("moving")){
                holder.img_status.setImageDrawable(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_ev_moving));
            }else if(event.equals("ignition_on")){
                holder.img_status.setImageDrawable(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_ignition_on));
            }else if(event.equals("ignition_off")){
                holder.img_status.setImageDrawable(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_ignition_off));
            }else {
                holder.img_status.setImageDrawable(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_ev_undefined));
            }
        }

        @Override
        public int getItemCount() {
            return world4.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public ImageView img_status;
            public TextView tvEventId;
            public TextView tvDate;
            public TextView tvEvent;
            public TextView tvAddress;

            public MyViewHolder (View view){
                super(view);
                img_status = (ImageView)view.findViewById(R.id.img_status);
                tvEventId = (TextView)view.findViewById(R.id.tvEventId);
                tvDate = (TextView)view.findViewById(R.id.tvDate);
                tvEvent = (TextView)view.findViewById(R.id.tvEvent);
                tvAddress = (TextView)view.findViewById(R.id.tvAddress);
            }
        }
    }
}
