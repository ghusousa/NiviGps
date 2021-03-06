package com.nivilive.gps.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.firebase.messaging.FirebaseMessaging;
import com.nivilive.gps.R;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.firebase.SharedPrefManager;
import com.nivilive.gps.ui.navigation.AppNavigator;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

import static com.nivilive.gps.data.Prefs.PREF_KEY_NOTIFICATION;
import static com.nivilive.gps.data.Prefs.PREF_KEY_SOUND;
import static com.nivilive.gps.data.Prefs.PREF_KEY_VIBRATE;

public class MainActivity extends DaggerAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String APP_PNAME = "com.gmaxgps" ;
    @Inject
	NavigatorHolder navigatorHolder;
	@Inject
	Router router;
	@Inject
	SharedPreferences prefs;

	Toolbar toolbar;
	DrawerLayout drawer;
	NavigationView navigationView;
	ActionBarDrawerToggle toggle;

	private AppNavigator navigator = new AppNavigator(this, getSupportFragmentManager());

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
		initControls();
		router.newRootScreen(Screens.TRACKING_FRAGMENT);
		setTitle(getString(R.string.tracking_title));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (!drawer.isDrawerOpen(GravityCompat.START)) {
				drawer.openDrawer(GravityCompat.START);
			} else {
				drawer.closeDrawer(GravityCompat.START);
			}
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void initControls() {
		toolbar = findViewById(R.id.toolbar);
		drawer = findViewById(R.id.drawer_layout);
		navigationView = findViewById(R.id.nav_view);
		toggle = new ActionBarDrawerToggle(this,
				drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
		setSupportActionBar(toolbar);
	}

	@Override
	protected void onResume() {
		super.onResume();
		navigatorHolder.setNavigator(navigator);
	}

	@Override
	protected void onPause() {
		navigatorHolder.removeNavigator();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_tracking:
				toolbar.setTitle(item.getTitle());
				router.replaceScreen(Screens.TRACKING_FRAGMENT);
				break;
            /*case R.id.action_devices:
                toolbar.setTitle(item.getTitle());
                commingSoon();
                break;*/
			case R.id.action_driver:
				toolbar.setTitle(item.getTitle());
				router.replaceScreen(Screens.DRIVER_FRAGMENT);
				break;
            /*case R.id.action_alert:
                toolbar.setTitle(item.getTitle());
                //    commingSoon();
                Intent i = new Intent(MainActivity.this, Notifications.class);
                startActivity(i);
                break;*/
			case R.id.action_notifications:
				toolbar.setTitle(item.getTitle());
				router.replaceScreen(Screens.NOTIFICATIONS_FRAGMENT);
				break;
/*            case R.id.action_alerts_settings:
                dialogAlertSettings();
                break;
            case R.id.action_user_profile:
                toolbar.setTitle(item.getTitle());
                break;*/
			case R.id.action_privacy_policy:
                toolbar.setTitle(item.getTitle());
             // router.replaceScreen(Screens.POLICY_SCREEN);
			    policy();
			    break;
            case R.id.action_terms:
                toolbar.setTitle(item.getTitle());
                terms();
                break;
            case R.id.action_contactus:
                toolbar.setTitle(item.getTitle());
                contactUs();
                break;
/*            case R.id.action_feedback:
                feedback();
                break;
            case R.id.action_share_app:
                shareApp();
                break;
            case R.id.action_rate_us:
                rateUs();
                break;*/
            case R.id.action_logout:
                toolbar.setTitle(item.getTitle());
                logout();
                break;
		}
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

    private void dialogAlertSettings() {
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_alert, null);
        final android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        alert.setView(promptView);
        Switch swNotification = (Switch) promptView.findViewById(R.id.swNotification);
        Switch swVibrate = (Switch) promptView.findViewById(R.id.swVibrate);
        Switch swSound = (Switch) promptView.findViewById(R.id.swSound);

        Log.d("PrefSwitch", SharedPrefManager.getBoolPreferences(MainActivity.this, PREF_KEY_NOTIFICATION) + " - " + SharedPrefManager.getBoolPreferences(MainActivity.this, PREF_KEY_VIBRATE) + " - " + SharedPrefManager.getBoolPreferences(MainActivity.this, PREF_KEY_SOUND));
        swNotification.setChecked(SharedPrefManager.getBoolPreferences(MainActivity.this, PREF_KEY_NOTIFICATION));
        swVibrate.setChecked(SharedPrefManager.getBoolPreferences(MainActivity.this, PREF_KEY_VIBRATE));
        swSound.setChecked(SharedPrefManager.getBoolPreferences(MainActivity.this, PREF_KEY_SOUND));

        final Button btn_ok = (Button) promptView.findViewById(R.id.btn_ok);
        final Button btn_cancel = (Button) promptView.findViewById(R.id.btn_cancel);

        final android.support.v7.app.AlertDialog alert1 = alert.create();
        alert1.setCanceledOnTouchOutside(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert1.dismiss();
                Log.d("SwitchStatus", swNotification.isChecked() + " - " + swVibrate.isChecked() + " - " + swSound.isChecked());
                SharedPrefManager.saveBoolPreferences(MainActivity.this, PREF_KEY_NOTIFICATION, swNotification.isChecked());
                SharedPrefManager.saveBoolPreferences(MainActivity.this, PREF_KEY_VIBRATE, swVibrate.isChecked());
                SharedPrefManager.saveBoolPreferences(MainActivity.this, PREF_KEY_SOUND, swSound.isChecked());
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert1.dismiss();

            }
        });
        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert1.show();
    }
    private void logout() {
		new AlertDialog.Builder(this)
				.setMessage(R.string.tracking_dialog_logout_confirm)
				.setPositiveButton(R.string.button_logout, ((dialog, which) -> finishLogout()))
				.setNegativeButton(R.string.button_cancel, null)
				.show();
	}

	private void finishLogout() {
		prefs.edit()
				.remove(Prefs.PREF_KEY_REMEMBER_ME)
				.remove(Prefs.PREF_KEY_AUTHORIZATION)
				.apply();
		router.replaceScreen(Screens.SIGN_IN_SCREEN);
	}

	private void policy(){
       	Intent i = new Intent(MainActivity.this,PolicyActivity.class);
		startActivity(i);
    }

    private void terms(){
	    Intent i = new Intent(MainActivity.this,TermsActivity.class);
	    startActivity(i);
    }

    private void contactUs(){
        Intent i = new Intent(MainActivity.this,ContactUsActivity.class);
        startActivity(i);

    }

    private void feedback() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "info@nivilive.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback - Nivi GPS");
        intent.putExtra(Intent.EXTRA_TEXT, "Nice App! Yor are doing good.");
        startActivity(Intent.createChooser(intent, "Send Feddback"));
    }

    private void rateUs(){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
        } catch (Exception e) {
            e.printStackTrace();
            somethingWentWrong();
        }


    }

    private void shareApp(){

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT,"Nivi GPS");
            String sAux= "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.gmaxgps \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i,"choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void commingSoon(){
        Snackbar.make(findViewById(android.R.id.content), "We are coming Soon", Snackbar.LENGTH_LONG)
                //       .setAction("Undo", mOnClickListener)
                .setActionTextColor(Color.RED)
                .show();
    }

    private void somethingWentWrong(){
        Snackbar.make(findViewById(android.R.id.content), "Looks Something went Wrong", Snackbar.LENGTH_LONG)
                //       .setAction("Undo", mOnClickListener)
                .setActionTextColor(Color.RED)
                .show();

    }

}