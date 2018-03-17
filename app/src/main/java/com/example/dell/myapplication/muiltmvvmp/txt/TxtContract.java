package com.example.dell.myapplication.muiltmvvmp.txt;

import com.example.dell.myapplication.mvp.BaseView;

import io.reactivex.Observable;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 10:54
 */
public interface TxtContract {

    interface Presenter {
        void doSomething();
    }

    interface View extends BaseView<TxtContract.Presenter> {
        String getText();

        Observable<String> getTextCallBack();

        void setText(String txt);

        void loading(boolean b);

        void clear();
    }
}
