package com.example.bin.myapplication.mvcp.btn;

import android.widget.Button;

import com.example.bin.myapplication.mvp.BaseView;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 10:54
 */
public interface BtnContract {

    interface Presenter {
        int getImg();

        String doSomething();
    }

    interface View extends BaseView<Presenter> {
        void setText(Button btn, String txt);
    }
}
