package com.nivilive.gps.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;

import dagger.android.support.DaggerFragment;


public abstract class BaseFragment extends DaggerFragment {

	private boolean stateSaved;
	private MvpDelegate mvpDelegate;

	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getMvpDelegate().onCreate(savedInstanceState);
	}

	@NonNull
	public final MvpDelegate getMvpDelegate() {
		if (this.mvpDelegate == null) {
			this.mvpDelegate = new MvpDelegate(this);
		}
		return this.mvpDelegate;
	}

	@Nullable
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(this.getLayoutRes(), container, false);
	}

	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		this.initControls(view);
	}

	@LayoutRes
	protected abstract int getLayoutRes();

	protected void initControls(@NonNull View v) {

	}

	public void onStart() {
		super.onStart();
		this.stateSaved = false;
		this.getMvpDelegate().onAttach();
	}

	public void onResume() {
		super.onResume();
		this.stateSaved = false;
		this.getMvpDelegate().onAttach();
	}

	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		this.stateSaved = true;
		this.getMvpDelegate().onSaveInstanceState(outState);
		this.getMvpDelegate().onDetach();
	}

	public void onStop() {
		super.onStop();
		this.getMvpDelegate().onDetach();
	}

	public void onDestroyView() {
		super.onDestroyView();
		this.getMvpDelegate().onDetach();
		this.getMvpDelegate().onDestroyView();
	}

	public void onDestroy() {
		super.onDestroy();
		FragmentActivity fragmentActivity = this.getActivity();
		if (fragmentActivity.isFinishing()) {
			this.getMvpDelegate().onDestroy();
		} else if (this.stateSaved) {
			this.stateSaved = false;
		} else {
			boolean anyParentIsRemoving = false;

			for (Fragment parent = this.getParentFragment(); !anyParentIsRemoving && parent != null; parent = parent.getParentFragment()) {
				anyParentIsRemoving = parent.isRemoving();
			}

			if (this.isRemoving() || anyParentIsRemoving) {
				this.getMvpDelegate().onDestroy();
			}

		}
	}

}