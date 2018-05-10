package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 16:49
 */
public interface Controller {

    @Nullable
    <T> T getUIController(@NonNull Class<T> cls);

    <T extends UIController> void addUIController (@NonNull Class<T> cls);

    <T extends UIController> void addUIController (@NonNull T uiController);
}
