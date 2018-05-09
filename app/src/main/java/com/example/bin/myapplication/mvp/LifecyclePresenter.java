package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 16:00
 */
public class LifecyclePresenter<V extends BaseView> implements ILifecycle {

    protected V view;

    public LifecyclePresenter(@NonNull V view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void create() {
    }

    @Override
    public void start() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void destroy() {
    }
}