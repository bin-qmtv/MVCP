package com.example.bin.myapplication.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import java.util.Map;
import java.util.Set;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:39
 */
public abstract class ControllerActivity extends BaseCleanActivity implements Controller {

    private ArrayMap<Class, Object> controllerProxyArrayMap = new ArrayMap<>();
    private ArrayMap<Class, UIController> controllerArrayMap = new ArrayMap<>();

    @Override
    @Nullable
    public <T> T getUIController(@NonNull Class<T> cls) {
        T proxy = (T) controllerProxyArrayMap.get(cls);
        if (proxy != null) {
            return proxy;
        }
        return (T) controllerArrayMap.get(cls);
    }

    @Override
    public <T extends UIController> void addUIController(@NonNull Class<T> cls) {
        UIController uiController = MvpFactory.newInstance(cls, this);
        if (uiController != null) addUIController(uiController);
    }

    @Override
    public <T extends UIController> void addUIController(@NonNull T uiController) {
        controllerArrayMap.put(uiController.getClass(), uiController);
        controllerProxyArrayMap.put(uiController.getClass(), MvpDelegate.newProxy(uiController));
    }

    @Override
    protected void init() {
        initUIController();
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
        for (Map.Entry<Class, UIController> controllerEntry : set) {
            UIController uiController = controllerEntry.getValue();
            if (uiController != null) uiController.onSaveState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
        for (Map.Entry<Class, UIController> controllerEntry : set) {
            UIController uiController = controllerEntry.getValue();
            if (uiController != null) uiController.onStateRestored(savedInstanceState);
        }
    }

    @Override
    public void initView() {
        Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
        for (Map.Entry<Class, UIController> controllerEntry : set) {
            UIController uiController = controllerEntry.getValue();
            if (uiController != null) uiController.initView();
        }
    }

    @Override
    public void onBackPressed() {
        Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
        boolean hasBackAction = false;
        for (Map.Entry<Class, UIController> controllerEntry : set) {
            UIController uiController = controllerEntry.getValue();
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
        Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
        for (Map.Entry<Class, UIController> controllerEntry : set) {
            UIController uiController = controllerEntry.getValue();
            if (uiController != null) {
                uiController.onResult(requestCode, resultCode, data);
            }
        }
    }

    public abstract void initUIController();
}
