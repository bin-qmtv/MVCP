package com.example.bin.myapplication.mvcp.img;

import android.util.Log;
import android.widget.ImageView;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;

import io.reactivex.Observable;

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
        if (img == null) img = findViewById(R.id.img);
        return Observable.create(emitter -> img.post(() -> emitter.onNext(true)));
    }

    //@Priority(1)
    @Override
    public boolean onBackPressed() {
        Log.d("---", "onBackPressed: " + getClass().getSimpleName());
        return super.onBackPressed();
    }
}
