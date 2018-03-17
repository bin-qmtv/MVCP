package com.example.dell.myapplication.test;


import com.example.dell.myapplication.mvp.BaseView;

public interface BlankContract {

    interface Presenter {
        void doSomething();
    }

    interface View extends BaseView<Presenter> {

    }

}
