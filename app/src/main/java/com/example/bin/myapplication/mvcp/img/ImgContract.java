package com.example.bin.myapplication.mvcp.img;

import com.example.bin.myapplication.mvp.BaseView;

import io.reactivex.Observable;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 10:54
 */
public interface ImgContract {

    interface Presenter {
        void doSomething();
    }

    interface View extends BaseView<Presenter> {
        void setImg(int resId);

        Observable<Boolean> doOnImgVisible();
    }
}
