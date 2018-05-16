package com.nivilive.gps.ui.signin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.nivilive.gps.R;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.base.BaseFragment;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class SignInFragment extends BaseFragment implements SignInView {

	@Inject
	SchedulersFactory schedulers;
	@Inject
	Router router;
	@Inject
	RestApi api;
	@Inject
	SharedPreferences prefs;
	@InjectPresenter
	SignInPresenter presenter;

	private EditText emailField;
	private EditText passwordField;
	private Button loginButton;
	private ProgressBar progress;
	private CheckBox rememberMe;
	private Button registrationLink;

	public static SignInFragment newInstance() {
		Bundle args = new Bundle();
		SignInFragment fragment = new SignInFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@ProvidePresenter
	public SignInPresenter providePresenter() {
		return new SignInPresenter(schedulers, router, api, prefs);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_sign_in;
	}

	@Override
	protected void initControls(@NonNull View v) {
		loginButton = v.findViewById(R.id.button_login);
		loginButton.setOnClickListener(v1 -> presenter.loginClick());
		emailField = v.findViewById(R.id.edit_email);
		passwordField = v.findViewById(R.id.edit_password);
		progress = v.findViewById(R.id.progress);
		rememberMe = v.findViewById(R.id.checkbox_remember_me);
		registrationLink = v.findViewById(R.id.button_registration);
//		registrationLink = v.findViewById(R.id.text_registration);
		initRegistrationLink();
	}

	private void initRegistrationLink() {
		registrationLink.setMovementMethod(LinkMovementMethod.getInstance());
		registrationLink.setOnClickListener(v -> presenter.onRegistrationClick());
	}

	@Override
	public void initFields() {
		presenter.setEmailChanges(RxTextView.textChanges(emailField));
		presenter.setPasswordChanges(RxTextView.textChanges(passwordField));
		presenter.setRememberMeChanges(RxCompoundButton.checkedChanges(rememberMe));
		presenter.startListeningFields();
	}

	@Override
	public void showLoading() {
		progress.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoading() {
		progress.setVisibility(View.GONE);
	}

	@Override
	public void enableLoginButton() {
		loginButton.setEnabled(true);
	}

	@Override
	public void disableLoginButton() {
		loginButton.setEnabled(false);
	}

	@Override
	public void displayError(@NonNull String error) {
		Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
	}

}