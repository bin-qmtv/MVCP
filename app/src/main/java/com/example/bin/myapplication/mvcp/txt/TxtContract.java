package com.example.bin.myapplication.mvcp.txt;

import com.example.bin.myapplication.mvp.BaseView;

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

    interface View extends BaseView<Presenter> {
        String getText();

        Observable<String> getTextCallBack();

        void setText(String txt);

        void loading(boolean b);

        void clear();
    }
}
