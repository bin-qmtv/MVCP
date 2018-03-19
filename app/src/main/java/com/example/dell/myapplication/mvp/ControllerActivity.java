package com.example.dell.myapplication.mvp;

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

    public ArrayMap<Class, UIController> controllerArrayMap = new ArrayMap<>();

    @Override
    public <T extends UIController> T getUIController(Class<T> cls) {
        return (T) controllerArrayMap.get(cls);
    }

    @Override
    public <T extends UIController> void addUIController(T t) {
        controllerArrayMap.put(t.getClass(), t);
    }

    public void initView() {
    }

    @Override
    public void onBackPressed() {
        Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
        boolean hasBackAction = false;
        for (Map.Entry<Class, UIController> controllerEntry : set) {
            UIController uiController = controllerEntry.getValue();
            if (uiController.onBackPressed()) {
                hasBackAction = true;
            }
        }
        if (!hasBackAction) {
            super.onBackPressed();
        }
    }
}
