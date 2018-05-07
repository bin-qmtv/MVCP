package com.example.bin.myapplication.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 17:00
 */
public abstract class MVPFragment<P> extends BaseCleanFragment {

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    public void setPresenter(P presenter) {
        this.presenter = PresenterDelegate.newProxy(presenter);
        if (presenter instanceof LifecycleObserver) {
            LifecycleObserver lifecycleObserver = (LifecycleObserver) presenter;
            getLifecycle().addObserver(lifecycleObserver);
        }
    }

    protected abstract void initPresenter();

    public void showLoading(boolean cancelable){}

    public void cancelLoading(){}

    public void showError(boolean show){}

    public void showEmpty(boolean show){}

    public void showOffline(boolean show){}
}
