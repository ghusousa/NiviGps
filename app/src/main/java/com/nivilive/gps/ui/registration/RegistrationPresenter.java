package com.nivilive.gps.ui.registration;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.data.model.RegisteredUser;
import com.nivilive.gps.mvp.BasePresenter;
import com.nivilive.gps.rx.SchedulersFactory;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class RegistrationPresenter extends BasePresenter<RegistrationView> {

	@NonNull
	private final SchedulersFactory schedulers;
	@NonNull
	private final RestApi api;
	@NonNull
	private final Router router;

	private Observable<CharSequence> nameChanges;
	private Observable<CharSequence> emailChanges;
	private Observable<CharSequence> passwordChanges;
	private String name = "";
	private String email = "";
	private String password = "";

	public RegistrationPresenter(@NonNull SchedulersFactory schedulers, @NonNull RestApi api, @NonNull Router router) {
		this.schedulers = schedulers;
		this.api = api;
		this.router = router;
	}

	@Override
	protected void onFirstViewAttach() {
		getViewState().initFields();
	}

	public void setNameChanges(Observable<CharSequence> nameChanges) {
		this.nameChanges = nameChanges;
	}

	public void setEmailChanges(Observable<CharSequence> emailChanges) {
		this.emailChanges = emailChanges;
	}

	public void setPasswordChanges(Observable<CharSequence> passwordChanges) {
		this.passwordChanges = passwordChanges;
	}

	public void startListeningFields() {
		unsubscribeOnDestroy(nameChanges
				.observeOn(schedulers.ui())
				.subscribe(it -> name = it.toString())
		);
		unsubscribeOnDestroy(emailChanges
				.observeOn(schedulers.ui())
				.subscribe(it -> email = it.toString())
		);
		unsubscribeOnDestroy(passwordChanges
				.observeOn(schedulers.ui())
				.subscribe(it -> password = it.toString())
		);
		Disposable disposable = Observable.zip(nameChanges, emailChanges, passwordChanges,
				(nameValue, emailValue, passwordValue) ->
						nameValue.length() > 0 && emailValue.length() > 0 && passwordValue.length() > 0
		)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(it -> {
					if (it) {
						getViewState().enableUpdateButton();
					} else {
						getViewState().disableUpdateButton();
					}
				});
		unsubscribeOnDestroy(disposable);
	}

	public void onUpClick() {
		router.exit();
	}

	public void onRegistrationClick() {
		getViewState().showLoading();
		RegisteredUser user = new RegisteredUser(name, email, password);
		unsubscribeOnDestroy(api.registerUser(user)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(user1 -> {
					getViewState().hideLoading();
					getViewState().displaySignUpSuccessDialog();
				}, throwable -> {
					getViewState().hideLoading();
					getViewState().showError("Sign up error!");
				})
		);
	}

	public void onCloseScreen() {
		router.exit();
	}

}