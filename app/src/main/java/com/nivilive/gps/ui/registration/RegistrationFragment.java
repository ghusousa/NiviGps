package com.nivilive.gps.ui.registration;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.nivilive.gps.R;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.base.BaseFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


public class RegistrationFragment extends BaseFragment implements RegistrationView {

	@Inject
	SchedulersFactory schedulers;
	@Inject
	Router router;
	@Inject
	RestApi api;
	@InjectPresenter
	RegistrationPresenter presenter;

	@ProvidePresenter
	public RegistrationPresenter providePresenter() {
		return new RegistrationPresenter(schedulers, api, router);
	}

	private Toolbar toolbar;
	private ProgressBar progress;
	private EditText nameField;
	private EditText emailField;
	private EditText passwordField;
	private Button registrationButton;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_registration;
	}

	@Override
	protected void initControls(@NonNull View v) {
		toolbar = v.findViewById(R.id.toolbar);
		progress = v.findViewById(R.id.progress);
		registrationButton = v.findViewById(R.id.button_registration);
		registrationButton.setOnClickListener(v1 -> presenter.onRegistrationClick());
		nameField = v.findViewById(R.id.edit_name);
		emailField = v.findViewById(R.id.edit_email);
		passwordField = v.findViewById(R.id.edit_password);
		initToolbar();
	}

	private void initToolbar() {
		toolbar.setTitle(R.string.registration_title);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
		toolbar.setNavigationOnClickListener(v1 -> presenter.onUpClick());
	}

	@Override
	public void initFields() {
		presenter.setNameChanges(RxTextView.textChanges(nameField));
		presenter.setEmailChanges(RxTextView.textChanges(emailField));
		presenter.setPasswordChanges(RxTextView.textChanges(passwordField));
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
	public void showError(@NonNull String message) {
		Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void enableUpdateButton() {
		registrationButton.setEnabled(true);
	}

	@Override
	public void disableUpdateButton() {
		registrationButton.setEnabled(false);
	}

	@Override
	public void displaySignUpSuccessDialog() {
		new AlertDialog.Builder(getActivity())
				.setMessage(R.string.registration_sign_up_success)
				.setPositiveButton(R.string.button_ok, (dialog, which) -> presenter.onCloseScreen())
                .setCancelable(false)
				.show();
	}
}