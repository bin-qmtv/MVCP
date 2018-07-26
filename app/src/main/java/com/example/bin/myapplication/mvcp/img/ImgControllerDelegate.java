package com.example.bin.myapplication.mvcp.img;

import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;

import io.reactivex.Observable;

public class ImgControllerDelegate extends UIController<ImgContract.Presenter> implements ImgContract.View {

    private final ImgController imgController;

    public ImgControllerDelegate(ControllerActivity controller, ImgController imgController) {
        super(controller);
        this.imgController = imgController;
    }

    @Override
    public void setImg(int resId) {
        imgController.setImg(resId);
    }

    @Override
    public Observable<Boolean> doOnImgVisible() {
        return imgController.doOnImgVisible();
    }

    @Override
    public void initView() {
        imgController.initView();
    }
}