package com.example.dell.myapplication.muiltmvvmp.btn;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.mvp.LifecyclePresenter;

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
        return null;
    }
}
