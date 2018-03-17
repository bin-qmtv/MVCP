package com.example.dell.myapplication.test;

import com.example.dell.myapplication.mvp.LifecyclePresenter;

public class BlankPresenter extends LifecyclePresenter<BlankContract.View> implements BlankContract.Presenter {

    private BlankViewModel viewModel;

    public BlankPresenter(BlankContract.View view, BlankViewModel viewModel) {
        super(view);
        this.viewModel = viewModel;
    }

    @Override
    public void start() {
        // fragment onStart时自动调用   Now -> start to fly
    }

    @Override
    public void doSomething() {

    }
}
