package com.example.bin.myapplication.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.example.bin.myapplication.mvp.annotation.Priority;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 18:39
 */
public abstract class ControllerActivity extends BaseCleanActivity implements Controller {

    private ArrayMap<String, Object> controllerProxyArrayMap = new ArrayMap<>();
    private ArrayMap<String, UIController> controllerArrayMap = new ArrayMap<>();
    private ArrayList<UIController> sortedUIControllers = new ArrayList<>();

    @Override
    @Nullable
    public <T> T getUIController(@NonNull Class<T> cls) {
        return (T) controllerProxyArrayMap.get(cls.getCanonicalName());
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
        sortedUIControllers.add(uiController);
        controllerArrayMap.put(uiController.getClass().getCanonicalName(), uiController);
        Object proxy = MvpFactory.newProxy(uiController);
        controllerProxyArrayMap.put(uiController.getClass().getCanonicalName(), proxy);
        if (view != null) controllerProxyArrayMap.put(view.getCanonicalName(), proxy);
    }

    @Override
    public int getUIControllerSize(){
        return controllerArrayMap.size();
    }

    @Override
    protected void init() {
        initUIController();
        sort();
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
        beforeUIControllersBackPressed();

        boolean hasBackAction = false;
        for (UIController uiController : sortedUIControllers) {
            if (uiController != null) {
                if (uiController.onBackPressed()) {
                    hasBackAction = true;
                }
                if (uiController.isAbortBackPressed()) {
                    uiController.setAbortBackPressed(false);
                    break;
                }
            }
        }

        if (!hasBackAction) {
            beforeSuperBackPressed();
            super.onBackPressed();
        }
    }

    public void beforeUIControllersBackPressed() {
        for (UIController uiController : sortedUIControllers) {
            if (uiController instanceof OnBackPressedAction) {
                ((OnBackPressedAction) uiController).beforeUIControllersBackPressed();
            }
        }
    }

    public void beforeSuperBackPressed() {
        for (UIController uiController : sortedUIControllers) {
            if (uiController instanceof OnBackPressedAction) {
                ((OnBackPressedAction) uiController).beforeSuperBackPressed();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onResult(requestCode, resultCode, data);
        }
    }

    private void sort() {
        if (sortedUIControllers == null || sortedUIControllers.size() < 2) {
            return;
        }
        Collections.sort(sortedUIControllers, (o1, o2) -> {
            int p1 = getPriority(o1);
            int p2 = getPriority(o2);
            return p2 - p1;
        });
    }

    private int getPriority (@NonNull UIController uiController) {
        Method[] backMethods = Backable.class.getMethods();
        if(backMethods.length > 0){
            String name = backMethods[0].getName();
            Method[] methods = uiController.getClass().getMethods();
            for (Method method : methods) {
                if(method != null) {
                    Priority priority = method.getAnnotation(Priority.class);
                    if (priority != null && TextUtils.equals(name, method.getName())) {
                        return priority.value();
                    }
                }
            }
        }
        return 0;
    }

    public abstract void initUIController();
}
