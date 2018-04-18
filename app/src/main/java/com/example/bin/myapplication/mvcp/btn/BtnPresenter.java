package com.example.bin.myapplication.mvcp.btn;

import android.util.Log;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.LifecyclePresenter;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 11:02
 */
public class BtnPresenter extends LifecyclePresenter<BtnContract.View> implements BtnContract.Presenter {

    private boolean flag;

    public BtnPresenter(BtnContract.View view) {
        super(view);
    }

    @Override
    public int getImg() {
        flag = !flag;
        return flag ? R.drawable.android : R.mipmap.ic_launcher;
    }

    @Override
    public String doSomething() {
        Log.d("---", "doSomething on img visible");
        return null;
    }
}
