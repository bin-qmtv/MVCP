package com.example.dell.myapplication.muiltmvvmp.img;

import android.widget.ImageView;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.mvp.ControllerActivity;
import com.example.dell.myapplication.mvp.UIController;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:37
 */
public class ImgController extends UIController<ImgContract.Presenter> implements ImgContract.View {

    private ImageView img;

    public ImgController(ControllerActivity controller) {
        super(controller);
    }

    @Override
    public void initPresenter() {
        new ImgPresenter(this);
    }

    @Override
    public void initView() {
        img = findViewById(R.id.img);
    }

    @Override
    public void setImg(int resId) {
        img.setImageResource(resId);
    }

    public Observable<Boolean> doOnImgVisible() {

        Observable<Boolean> observable = Observable.create(emitter -> img.post(() -> {
            emitter.onNext(true);
            emitter.onComplete();
        }));

        return observable;
    }
}
