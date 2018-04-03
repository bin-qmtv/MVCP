package com.example.bin.myapplication.mvp;

import android.content.Context;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 16:00
 */
public interface BaseView<P> {
    void setPresenter(P presenter);

    Context getContext();

}