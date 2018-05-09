package com.example.bin.myapplication.mvvmp;

import com.example.bin.myapplication.mvp.BaseView;

public interface Main2Contract {

    interface Presenter {
        void doSomething();
    }

    interface View extends BaseView<Presenter> {
        Main2ViewModel getViewModel();

        void setText(String txt);
    }

}
