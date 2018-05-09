package com.example.bin.myapplication.mvvmp;

import com.example.bin.myapplication.mvcp.Main3Activity;
import com.example.bin.myapplication.mvp.LifecyclePresenter;

public class Main2Presenter extends LifecyclePresenter<Main2Contract.View> implements Main2Contract.Presenter {

    public Main2Presenter(Main2Contract.View view) {
        super(view);
    }

    @Override
    public void create() {
        // Activity onCreate时自动调用   Now -> start to fly
        view.getViewModel().fetch()
                .doOnComplete(() -> {
                    view.setText("fetch data complete");
                })
                .subscribe();
    }

    @Override
    public void doSomething() {
        Main3Activity.start(view.getContext());
    }
}
