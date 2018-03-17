package com.example.dell.myapplication.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:32
 */
public abstract class UIController<P> {

    protected ControllerActivity controller;

    protected P presenter;

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected UIController(ControllerActivity controller) {
        this.controller = controller;
        init();
    }

    protected void init() {
        initPresenter();
        initView();
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return controller.findViewById(id);
    }

    public <T extends Activity> T getActivity() {
        return (T) controller;
    }

    public Context getContext() {
        return controller;
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
    }

    public <T> void onFirstLoad(T t){

    }

    public abstract void initPresenter();

    public abstract void initView();
}
