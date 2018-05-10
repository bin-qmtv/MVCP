package com.example.bin.myapplication.mvcp.btn;

import android.widget.Button;

import com.example.bin.myapplication.mvp.BaseView;
import com.example.bin.myapplication.mvp.annotation.PointBefore;
import com.example.bin.myapplication.mvp.annotation.PrintDuration;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 10:54
 */
public interface BtnContract {

    interface Presenter {
        @PointBefore(BtnImgPoint.class)
        int getImg();

        @PrintDuration
        String doSomething();
    }

    interface View extends BaseView<Presenter> {
        void setText(Button btn, String txt);
    }
}
