package com.nivilive.gps.ui.registration;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

public interface RegistrationView extends MvpView {

	void initFields();

	void showLoading();

	void hideLoading();

	void showError(@NonNull String message);

	void enableUpdateButton();

	void disableUpdateButton();

	void displaySignUpSuccessDialog();

}