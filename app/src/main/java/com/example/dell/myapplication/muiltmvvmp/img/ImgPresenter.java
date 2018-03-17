package com.example.dell.myapplication.muiltmvvmp.img;

import com.example.dell.myapplication.mvp.LifecyclePresenter;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 10:59
 */
public class ImgPresenter extends LifecyclePresenter<ImgContract.View> implements ImgContract.Presenter {

    public ImgPresenter(ImgContract.View view) {
        super(view);
    }

    @Override
    public void doSomething() {

    }
}
