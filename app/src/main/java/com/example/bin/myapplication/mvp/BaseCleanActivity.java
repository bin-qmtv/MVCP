package com.example.bin.myapplication.mvp;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bin.myapplication.R;

/**
 * 一个纯粹的基础Activity
 *
 * @author bin
 * @date 2017/12/5 15:14
 */
public abstract class BaseCleanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu.size() > 0) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem mi = menu.getItem(i);
                if (mi != null) {
                    Drawable drawable = mi.getIcon();
                    if (drawable != null) {
                        drawable.mutate();
                        drawable.setColorFilter(ContextCompat.getColor(this, R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
                    }
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
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