package com.example.dell.myapplication.muiltmvvmp.btn;

import android.widget.Button;
import android.widget.LinearLayout;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.muiltmvvmp.img.ImgController;
import com.example.dell.myapplication.muiltmvvmp.txt.TxtController;
import com.example.dell.myapplication.mvp.ControllerActivity;
import com.example.dell.myapplication.mvp.UIController;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:37
 */
public class BtnController extends UIController<BtnContract.Presenter> implements BtnContract.View {

    Button btn1;
    Button btn2;
    Button btn3;

    public BtnController(ControllerActivity controller) {
        super(controller);
    }

    @Override
    public void initPresenter() {
        new BtnPresenter(this);
    }

    @Override
    public void initView() {
        btn1 = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(v -> {
            int res = presenter.getImg();
            controller.getUIController(ImgController.class).setImg(res);
        });

        btn2.setOnClickListener(v -> {
            String txt = controller.getUIController(TxtController.class).getText();
            setText(btn2, txt);
        });

        btn3.setOnClickListener(v -> {
            controller.getUIController(TxtController.class).getTextCallBack()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> setText(btn3, s));
        });

        controller.getUIController(ImgController.class).doOnImgVisible().subscribe(visible->{
            // do on visible
            presenter.doSomething();
        });
    }

    @Override
    public void setText(Button btn, String txt) {
        btn.setText(txt);

        //(()controller).fetch();
    }
}
