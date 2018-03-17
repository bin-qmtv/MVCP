package com.example.dell.myapplication.mvp;

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
        this.presenter = presenter;
    }

    protected abstract void initPresenter();

    public void showLoading(boolean cancelable){}

    public void cancelLoading(){}

    public void showError(boolean show){}

    public void showEmpty(boolean show){}
}
