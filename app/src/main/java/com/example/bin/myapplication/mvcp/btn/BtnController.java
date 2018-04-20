package com.example.bin.myapplication.mvcp.btn;

import android.widget.Button;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvcp.img.ImgController;
import com.example.bin.myapplication.mvcp.txt.TxtController;
import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;
import com.example.bin.myapplication.ui.StateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

    StateView mStateView;

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
            mStateView = new StateView(getContext()).attachTo(btn3);
            controller.getUIController(TxtController.class).getTextCallBack()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mStateView.setState(StateView.STATE_LOADING);
                        }

                        @Override
                        public void onNext(String s) {
                            mStateView.setState(StateView.STATE_NONE);
                            btn3.setText(s);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mStateView.setState(StateView.STATE_ERROR);
                        }

                        @Override
                        public void onComplete() {
                            mStateView.detach();
                        }
                    });
        });

        ImgController imgController = controller.getUIController(ImgController.class);
        imgController.doOnImgVisible().subscribe(visible->{
            // do on img visible
            presenter.doSomething();
        });
    }

    @Override
    public void setText(Button btn, String txt) {
        btn.setText(txt);
    }
}
