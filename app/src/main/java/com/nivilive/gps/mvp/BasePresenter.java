package com.nivilive.gps.mvp;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

	private CompositeDisposable disposables = new CompositeDisposable();

	protected void unsubscribeOnDestroy(Disposable disposable) {
		disposables.add(disposable);
	}

	@Override
	public void onDestroy() {
		disposables.clear();
		super.onDestroy();
	}

}