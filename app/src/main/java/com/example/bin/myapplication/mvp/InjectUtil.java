package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;

import com.example.bin.myapplication.mvp.annotation.Presenter;

/**
 * description
 *
 * @author bin
 * @date 2018/5/9 10:45
 */
public class InjectUtil {

    public static <View extends BaseView> void injectPresenter(@NonNull View view) {
        Presenter presenterAnnotation = view.getClass().getAnnotation(Presenter.class);
        if (presenterAnnotation != null) {
            Class cls = presenterAnnotation.value();
            MvpFactory.newInstance(cls, view);
        }
    }
}
