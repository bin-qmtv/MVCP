package com.example.bin.myapplication.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:32
 */
public abstract class UIController<P> implements Backable {

    protected ControllerActivity controller;
    protected ControllerFragment controllerFragment;

    protected P presenter;

    public final void setPresenter(P presenter) {
        this.presenter = MvpFactory.newProxy(presenter);
        if (presenter instanceof LifecycleObserver) {
            LifecycleObserver lifecycleObserver = (LifecycleObserver) presenter;
            if (controllerFragment != null) {
                controllerFragment.getLifecycle().addObserver(lifecycleObserver);
            }else {
                controller.getLifecycle().addObserver(lifecycleObserver);
            }
        }
    }

    public UIController(ControllerActivity controller) {
        this.controller = controller;
        init();
    }

    public UIController(ControllerFragment controllerFragment) {
        this.controllerFragment = controllerFragment;
        FragmentActivity activity = controllerFragment.getActivity();
        if (activity != null && activity instanceof ControllerActivity) {
            controller = (ControllerActivity) activity;
        }
        init();
    }

    protected <T extends BaseView> T getUIController(Class<T> cls) {
        if (controllerFragment != null) {
            return controllerFragment.getUIController(cls);
        }
        return controller.getUIController(cls);
    }

    protected void init() {
        initPresenter();
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (controllerFragment != null) {
            return controllerFragment.findViewById(id);
        }
        if (controller != null) {
            return controller.findViewById(id);
        }
        return null;
    }

    public Controller getController() {
        if (controllerFragment != null) {
            return controllerFragment;
        }
        return controller;
    }

    public ControllerActivity getControllerActivity() {
        return controller;
    }

    public ControllerFragment getControllerFragment() {
        return controllerFragment;
    }

    public Context getContext() {
        if (controllerFragment != null) {
            return controllerFragment.getActivity();
        }
        return controller;
    }

    protected void initPresenter(){
        if (this instanceof BaseView) {
            InjectUtil.injectPresenter((BaseView)this);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
    }

    public void onPostResume() {
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
    }

    public void onSaveState(Bundle outState) {
    }

    public void onStateRestored(Bundle savedState) {
    }

    public boolean onBackPressed() {
        return false;
    }

    public abstract void initView();

}
