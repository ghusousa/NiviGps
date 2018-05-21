package com.nivilive.gps.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nivilive.gps.R;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.ui.navigation.AppNavigator;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class SignInActivity extends DaggerAppCompatActivity {

	@Inject
	NavigatorHolder navigatorHolder;
	@Inject
	Router router;
	@Inject
	SharedPreferences prefs;

	private AppNavigator navigator = new AppNavigator(this, getSupportFragmentManager());

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		router.navigateTo(Screens.SIGN_IN_FRAGMENT);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isRememberMe()) {
			router.replaceScreen(Screens.MAIN_SCREEN);
		}
	}

	private boolean isRememberMe() {
		if (prefs.contains(Prefs.PREF_KEY_REMEMBER_ME) && prefs.getBoolean(Prefs.PREF_KEY_REMEMBER_ME, false)) {
			String token = prefs.getString(Prefs.PREF_KEY_AUTHORIZATION, "");
			return !token.isEmpty();
		}
		return false;
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
	//TODO  add code for onbackpress
	@Override
	public void onBackPressed(){
	    finish();

	}

}