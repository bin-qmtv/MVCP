package com.example.bin.myapplication.mvp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.bin.myapplication.R;

/**
 * description
 *
 * @author bin
 * @date 2018/6/13 20:50
 */
public class ToolBarUtil {

    static class Builder {
        Toolbar toolbar;
        CharSequence title;
        boolean enableHomeAsUp;
        Drawable drawable;
        @DrawableRes
        int resId;
        int arrowColor;
        View.OnClickListener clickListener;

        Builder(Toolbar toolbar) {
            this.toolbar = toolbar;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setEnableHomeAsUp(boolean enableHomeAsUp) {
            this.enableHomeAsUp = enableHomeAsUp;
            return this;
        }

        public Builder setDrawable(Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public Builder setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public Builder setArrowColor(int arrowColor) {
            this.arrowColor = arrowColor;
            return this;
        }

        public Builder setClickListener(View.OnClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        private DrawerArrowDrawable getArrowDrawable(Context context, int color) {
            DrawerArrowDrawable dad = new DrawerArrowDrawable(context);
            if (color != 0) dad.setColor(color);
            dad.setProgress(1);
            return dad;
        }

        private DrawerArrowDrawable getDrawerDrawable(Context context, int color) {
            DrawerArrowDrawable dad = new DrawerArrowDrawable(context);
            if (color != 0) dad.setColor(color);
            dad.setProgress(0);
            return dad;
        }

        public void setup(final AppCompatActivity activity) {
            if (activity == null) return;
            if (toolbar == null) return;
            if (title == null) title = "";
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
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
                        if (resId != 0) {
                            Drawable drawable = ContextCompat.getDrawable(activity, resId);
                            drawable.mutate();
                            drawable.setColorFilter(
                                    ContextCompat.getColor(activity, R.color.textColorPrimary),
                                    PorterDuff.Mode.SRC_ATOP);
                            actionBar.setHomeAsUpIndicator(drawable);
                        } else {
                            actionBar.setHomeAsUpIndicator(getArrowDrawable(activity, arrowColor));
                        }
                    }
                    if (clickListener != null) {
                        toolbar.setNavigationOnClickListener(clickListener);
                    } else {
                        toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
                    }
                }
            }
        }
    }

    public static Builder newBuilder(Toolbar toolbar) {
        return new Builder(toolbar);
    }

    public static void init(AppCompatActivity activity, Toolbar toolbar, CharSequence title) {
        newBuilder(toolbar).setTitle(title).setEnableHomeAsUp(true).setup(activity);
    }
}
