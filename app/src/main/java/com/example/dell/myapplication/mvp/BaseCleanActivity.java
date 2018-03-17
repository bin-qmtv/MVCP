package com.example.dell.myapplication.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

/**
 * 一个纯粹的基础Activity（只保留埋点）
 *
 * @author bin
 * @date 2017/12/5 15:14
 */
public abstract class BaseCleanActivity extends DefaultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
    }

    /**
     * 布局文件
     * @return int layoutId R.layout.xxxxxx
     */
    protected abstract int getLayoutId();

    protected void init(){
        initView();
    }

    /**
     * 初始化
     */
    protected abstract void initView();

    public Context getContext() {
        return this;
    }

    public <T extends Activity> T getActivity () {
        return (T)this;
    }
}