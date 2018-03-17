package com.example.dell.myapplication.mvvmp;

import com.example.dell.myapplication.mvp.BaseView;

public interface Main2Contract {

    interface Presenter {

        void doSomething();
    }

    interface View extends BaseView<Presenter> {
        void setText(String txt);
    }

}
