package com.example.bin.myapplication.mvcp.btn;

import android.widget.Button;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvcp.img.ImgController;
import com.example.bin.myapplication.mvcp.txt.TxtController;
import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:37
 */
public class BtnController extends UIController<BtnContract.Presenter> implements BtnContract.View {

    @BindView(R.id.btn)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
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
        ButterKnife.bind(this, controller);

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
    }
}
