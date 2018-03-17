package com.example.dell.myapplication.muiltmvvmp.img;

import com.example.dell.myapplication.mvp.BaseView;

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

    interface View extends BaseView<ImgContract.Presenter> {
        void setImg(int resId);
    }
}
