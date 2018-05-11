package com.example.bin.myapplication.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:39
 */
public abstract class ControllerActivity extends BaseCleanActivity implements Controller {

    private ArrayMap<String, Object> controllerProxyArrayMap = new ArrayMap<>();
    private ArrayMap<String, UIController> controllerArrayMap = new ArrayMap<>();

    @Override
    @Nullable
    public <T> T getUIController(@NonNull Class<T> cls) {
        return (T) controllerProxyArrayMap.get(cls.getCanonicalName());
    }

    @Override
    public <T extends UIController> void addUIController(@NonNull Class<T> cls) {
        UIController uiController = MvpFactory.newInstance(cls, this);
        if (uiController != null) addUIController(uiController);
    }

    @Override
    public void addUIController(@NonNull UIController uiController) {
        Class cls = null;
        if (uiController instanceof BaseView) {
            cls = InjectUtil.findBaseView(uiController.getClass());
        }
        addUIController(uiController, cls);
    }

    @Override
    public <V extends BaseView> void addUIController(@NonNull UIController uiController, Class<V> view) {
        controllerArrayMap.put(uiController.getClass().getCanonicalName(), uiController);
        Object proxy = MvpFactory.newProxy(uiController);
        controllerProxyArrayMap.put(uiController.getClass().getCanonicalName(), proxy);
        if (view != null) controllerProxyArrayMap.put(view.getCanonicalName(), proxy);
    }

    @Override
    protected void init() {
        initUIController();
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onPostResume();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onSaveState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onStateRestored(savedInstanceState);
        }
    }

    @Override
    public void initView() {
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.initView();
        }
    }

    @Override
    public void onBackPressed() {
        boolean hasBackAction = false;
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null && uiController.onBackPressed()) {
                hasBackAction = true;
            }
        }
        if (!hasBackAction) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onResult(requestCode, resultCode, data);
        }
    }

    public abstract void initUIController();
}
