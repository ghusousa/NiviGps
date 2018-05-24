package com.nivilive.gps.ui.signin;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.nivilive.gps.data.Prefs;
import com.nivilive.gps.data.api.RestApi;
import com.nivilive.gps.mvp.BasePresenter;
import com.nivilive.gps.rx.SchedulersFactory;
import com.nivilive.gps.ui.Screens;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Credentials;
import ru.terrakok.cicerone.Router;


@InjectViewState
public class SignInPresenter extends BasePresenter<SignInView> {

	@NonNull
	private final SchedulersFactory schedulers;
	@NonNull
	private final Router router;
	@NonNull
	private final RestApi api;
	@NonNull
	private final SharedPreferences prefs;


	public SignInPresenter(@NonNull SchedulersFactory schedulers,
	                       @NonNull Router router,
	                       @NonNull RestApi api,
	                       @NonNull SharedPreferences prefs) {
		this.schedulers = schedulers;
		this.router = router;
		this.api = api;
		this.prefs = prefs;
	}

	private Observable<CharSequence> emailChanges;
	private Observable<CharSequence> passwordChanges;
	private Observable<Boolean> rememberMeChanges;
	private String email = "";
	private String password = "";
	private boolean rememberMe = false;

	@Override
	protected void onFirstViewAttach() {
		checkRememberMe();
		getViewState().disableLoginButton();
		getViewState().initFields();
	}

	private void checkRememberMe() {
		if (prefs.contains(Prefs.PREF_KEY_REMEMBER_ME) && prefs.getBoolean(Prefs.PREF_KEY_REMEMBER_ME, false)) {
			String token = prefs.getString(Prefs.PREF_KEY_AUTHORIZATION, "");
			if (!token.isEmpty()) {
				Observable.just(0)
						.delay(500, TimeUnit.MILLISECONDS)
						.subscribeOn(schedulers.computation())
						.observeOn(schedulers.ui())
						.subscribe(integer -> router.newRootScreen(Screens.TRACKING_FRAGMENT));
			}
		}
	}

	public void setEmailChanges(Observable<CharSequence> emailChanges) {
		this.emailChanges = emailChanges;
	}

	public void setPasswordChanges(Observable<CharSequence> passwordChanges) {
		this.passwordChanges = passwordChanges;
	}

	public void setRememberMeChanges(Observable<Boolean> rememberMeChanges) {
		this.rememberMeChanges = rememberMeChanges;
	}

	public void startListeningFields() {
		unsubscribeOnDestroy(emailChanges
				.observeOn(schedulers.ui())
				.subscribe(s -> email = s.toString())
		);
		unsubscribeOnDestroy(passwordChanges
				.observeOn(schedulers.ui())
				.subscribe(s -> password = s.toString())
		);
		unsubscribeOnDestroy(rememberMeChanges
				.observeOn(schedulers.ui())
				.subscribe(aBoolean -> rememberMe = aBoolean)
		);
		Disposable disposable = Observable.zip(emailChanges, passwordChanges,
				(emailValue, passwordValue) -> emailValue.length() > 0 && passwordValue.length() > 0)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(aBoolean -> {
					if (aBoolean) {
						getViewState().enableLoginButton();
					} else {
						getViewState().disableLoginButton();
					}
				});
		unsubscribeOnDestroy(disposable);
	}

	public void loginClick() {
		getViewState().showLoading();
		final String token = Credentials.basic(email, password);
		unsubscribeOnDestroy(api.addSession(email,password)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.ui())
				.subscribe(positions -> {
					getViewState().hideLoading();
					saveTokenInfo(token);
					router.newRootScreen(Screens.MAIN_SCREEN);
				}, throwable -> {
					getViewState().hideLoading();
					getViewState().displayError("Incorrect email/password");
				})
		);
	}

	private void saveTokenInfo(String token) {
		prefs.edit()
				.putBoolean(Prefs.PREF_KEY_REMEMBER_ME, rememberMe)
				.putString(Prefs.PREF_KEY_AUTHORIZATION, token)
				.apply();
	}

	public void onRegistrationClick() {
		router.navigateTo(Screens.REGISTRATION_FRAGMENT);
	}

}