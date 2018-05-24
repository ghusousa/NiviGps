package com.nivilive.gps.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.nivilive.gps.R;
import com.nivilive.gps.ui.MainActivity;
import com.nivilive.gps.ui.PolicyActivity;
import com.nivilive.gps.ui.Screens;
import com.nivilive.gps.ui.SignInActivity;
import com.nivilive.gps.ui.driver.DriverFragment;
import com.nivilive.gps.ui.notifications.NotificationsFragment;
import com.nivilive.gps.ui.registration.RegistrationFragment;
import com.nivilive.gps.ui.signin.SignInFragment;
import com.nivilive.gps.ui.tracking.TrackingFragment;

import ru.terrakok.cicerone.android.SupportAppNavigator;

public class AppNavigator extends SupportAppNavigator {

	@NonNull
	private final FragmentActivity activity;

	public AppNavigator(@NonNull FragmentActivity activity, FragmentManager fragmentManager) {
		super(activity, fragmentManager, R.id.container);
		this.activity = activity;
	}

	@Override
	protected Intent createActivityIntent(Context context, String screenKey, Object data) {
		switch (screenKey) {
			case Screens.MAIN_SCREEN:
				return new Intent(activity, MainActivity.class);
			case Screens.SIGN_IN_SCREEN:
				return new Intent(activity, SignInActivity.class);
            case Screens.POLICY_SCREEN:
                return new Intent(activity, PolicyActivity.class);
		}
		return null;
	}

	@Override
	protected Fragment createFragment(String screenKey, Object data) {
		switch (screenKey) {
			case Screens.SIGN_IN_FRAGMENT:
				return new SignInFragment();
			case Screens.TRACKING_FRAGMENT:
				return new TrackingFragment();
			case Screens.NOTIFICATIONS_FRAGMENT:
				return new NotificationsFragment();
			case Screens.DRIVER_FRAGMENT:
				return new DriverFragment();
			case Screens.REGISTRATION_FRAGMENT:
				return new RegistrationFragment();
			default:
				return new SignInFragment();
		}
	}

}