package com.example.dell.myapplication.mvp;

import android.support.v4.util.ArrayMap;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dell.myapplication.R;

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
}
