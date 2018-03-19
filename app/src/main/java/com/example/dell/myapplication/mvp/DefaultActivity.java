package com.example.dell.myapplication.mvp;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dell.myapplication.R;

/**
 * 所有Activity需要继承的基类
 */
public class DefaultActivity extends AppCompatActivity {

    protected DrawerArrowDrawable dad;

    protected void initToolBar(Toolbar toolbar) {
        initToolBar(toolbar, null, true, null);
    }

    protected void initToolBar(Toolbar toolbar, int arrowColor) {
        initToolBar(toolbar, arrowColor, null);
    }

    protected void initToolBar(Toolbar toolbar, Drawable drawable) {
        initToolBar(toolbar, drawable, null);
    }

    protected void initToolBar(Toolbar toolbar, String title) {
        initToolBar(toolbar, null , title);
    }

    protected void initToolBar(Toolbar toolbar, int arrowColor, String title) {
        initToolBar(toolbar, title, true, null, arrowColor, null);
    }

    protected void initToolBar(Toolbar toolbar, Drawable drawable, String title) {
        initToolBar(toolbar, title, true, drawable, 0, null);
    }

    protected void initToolBar(Toolbar toolbar, Drawable drawable, String title, View.OnClickListener clickListener) {
        initToolBar(toolbar, title, true, drawable, 0, clickListener);
    }

    protected void initToolBar(Toolbar toolbar, String title, boolean enableHomeAsUp) {
        initToolBar(toolbar, title, enableHomeAsUp, null);
    }

    protected void initToolBar(Toolbar toolbar, String title, View.OnClickListener clickListener) {
        initToolBar(toolbar, title, true, clickListener);
    }

    protected void initToolBar(Toolbar toolbar, String title, boolean enableHomeAsUp, View.OnClickListener clickListener) {
        initToolBar(toolbar, title, enableHomeAsUp, 0, clickListener);
    }

    protected void initToolBar(Toolbar toolbar, String title, boolean enableHomeAsUp, @DrawableRes int resId, View.OnClickListener clickListener) {
        Drawable drawable = null;

        if (resId != 0) {
            drawable = ContextCompat.getDrawable(this, resId);
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
        }
        initToolBar(toolbar, title, enableHomeAsUp, drawable, ContextCompat.getColor(this, R.color.textColorPrimary), clickListener);
    }

    /**
     * 初始化toolbar，自己加的TextView必须制定id为title
     *
     * @param title          标题
     * @param toolbar        toolbar引用
     * @param enableHomeAsUp 是否显示左边的图标
     * @param drawable       左边导航图标资源, 如果没有就加载默认的图标
     * @param arrowColor     左边导航图标颜色
     * @param clickListener  点击事件
     */
    protected void initToolBar(Toolbar toolbar, String title, boolean enableHomeAsUp, Drawable drawable, int arrowColor, View.OnClickListener clickListener) {
        if (toolbar == null) return;
        if (title == null) title = "";
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            TextView titleView = toolbar.findViewById(R.id.title);
            if (titleView != null) {
                actionBar.setDisplayShowTitleEnabled(false);
                if (!TextUtils.isEmpty(title)) titleView.setText(title);
            } else {
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(title);
            }
            actionBar.setDisplayHomeAsUpEnabled(enableHomeAsUp);
            if (enableHomeAsUp) {
                if (drawable != null) {
                    actionBar.setHomeAsUpIndicator(drawable);
                } else {
                    actionBar.setHomeAsUpIndicator(getArrowDrawable(arrowColor));
                }
                if (clickListener != null) {
                    toolbar.setNavigationOnClickListener(clickListener);
                } else {
                    toolbar.setNavigationOnClickListener(v -> onBackPressed());
                }
            }

        }
    }

    protected DrawerArrowDrawable getArrowDrawable(int color) {
        dad = new DrawerArrowDrawable(this);
        if (color != 0) dad.setColor(color);
        dad.setProgress(1);
        return dad;
    }

    protected DrawerArrowDrawable getDrawerDrawable(int color) {
        dad = new DrawerArrowDrawable(this);
        if (color != 0) dad.setColor(color);
        dad.setProgress(0);
        return dad;
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
}
