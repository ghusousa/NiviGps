package com.nivilive.gps.ui.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.nivilive.gps.R;
import com.nivilive.gps.ui.signin.SignInFragment;

import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class Navigator extends SupportFragmentNavigator {

	private final FragmentManager fragmentManager;

	public Navigator(FragmentManager fragmentManager) {
		super(fragmentManager, R.id.container);
		this.fragmentManager = fragmentManager;
	}

	@Override
	protected Fragment createFragment(String screenKey, Object data) {
		switch (screenKey) {
//			case Screens.SIGN_IN_SCREEN:
//				return new SignInFragment();
//			case Screens.TRACKING_FRAGMENT:
//				return new TrackingFragment();
//			case Screens.NOTIFICATIONS_FRAGMENT:
//				return new NotificationsFragment();
//			case Screens.DRIVER_FRAGMENT:
//				return new DriverFragment();
//			case Screens.REGISTRATION_FRAGMENT:
//				return new RegistrationFragment();
			default:
				return new SignInFragment();
		}
	}

	@Override
	protected void showSystemMessage(String message) {

	}

	@Override
	protected void exit() {
		fragmentManager.popBackStackImmediate();
	}

}