package com.nivilive.gps.ui.signin;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

public interface SignInView extends MvpView {

	void initFields();

	void showLoading();

	void hideLoading();

	void enableLoginButton();

	void disableLoginButton();

	void displayError(@NonNull String error);

}