package com.nivilive.gps.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nivilive.gps.R;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.ui.navigation.AppNavigator;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

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

			// Map activity
			// this activity shows map with all live vehicles
			case R.id.action_tracking:
				toolbar.setTitle(item.getTitle());
				router.replaceScreen(Screens.TRACKING_FRAGMENT);
				break;

			// device/vehicle list
            case R.id.action_devices:
                break;

			//Driver info Activity
			// shows drivers list planning to remove
			case R.id.action_driver:
				toolbar.setTitle(item.getTitle());
				router.replaceScreen(Screens.DRIVER_FRAGMENT);
				break;

			//firebase notification
            case R.id.action_alert:
                break;

			// Reports Activity
			// written for traditional notification. required to change/replace/delete
			case R.id.action_notifications:
				toolbar.setTitle(item.getTitle());
				router.replaceScreen(Screens.NOTIFICATIONS_FRAGMENT);
				break;

				//firebase alert settings
            case R.id.action_alerts_settings:
                toolbar.setTitle(item.getTitle());
                break;

            case R.id.action_user_profile:
                toolbar.setTitle(item.getTitle());
                break;

			case R.id.action_privacy_policy:
                toolbar.setTitle(item.getTitle());
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

            case R.id.action_feedback:
      //          toolbar.setTitle(item.getTitle());
                feedback();
                break;

            case R.id.action_share_app:
     //           toolbar.setTitle(item.getTitle());
                shareApp();
                break;

            case R.id.action_rate_us:
     //           toolbar.setTitle(item.getTitle());
                rateUs();
                break;

            case R.id.action_logout:                    //Logout
                toolbar.setTitle(item.getTitle());
                logout();
                break;


		}
		drawer.closeDrawer(GravityCompat.START);
		return true;
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
        } finally {

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

}