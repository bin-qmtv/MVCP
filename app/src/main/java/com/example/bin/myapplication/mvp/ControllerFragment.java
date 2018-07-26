package com.example.bin.myapplication.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bin.myapplication.mvp.annotation.Priority;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * description
 *
 * @author bin
 * @date 2018/4/13 10:07
 */
public abstract class ControllerFragment extends BaseCleanFragment implements Controller, Backable {

    public ArrayMap<String, Object> controllerProxyArrayMap = new ArrayMap<>();
    public ArrayMap<String, UIController> controllerArrayMap = new ArrayMap<>();
    private List<FragmentLifecycle> mFragmentLifecycle = new ArrayList<>();
    private ArrayList<UIController> sortedUIControllers = new ArrayList<>();
    private boolean isAbortBackPressed;

    public void addFragmentLifecycle(@NonNull FragmentLifecycle fragmentLifecycle) {
        if (mFragmentLifecycle == null) {
            mFragmentLifecycle = new ArrayList<>();
        }
        mFragmentLifecycle.add(fragmentLifecycle);
    }

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
        if (uiController instanceof FragmentLifecycle) {
            addFragmentLifecycle((FragmentLifecycle) uiController);
        }
    }

    @Override
    public int getUIControllerSize(){
        return controllerArrayMap.size();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUIController();
        sort();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mFragmentLifecycle != null && mFragmentLifecycle.size() > 0) {
            for (FragmentLifecycle fragmentLifecycle : mFragmentLifecycle) {
                fragmentLifecycle.onCreateView(view);
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragmentLifecycle != null && mFragmentLifecycle.size() > 0) {
            for (FragmentLifecycle fragmentLifecycle : mFragmentLifecycle) {
                fragmentLifecycle.onDestroyView();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onSaveState(outState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (UIController uiController : controllerArrayMap.values()) {
            if (uiController != null) uiController.onResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onBackPressed() {
        boolean hasBackAction = false;

        for (UIController uiController : sortedUIControllers) {
            if (uiController != null) {
                if (uiController.onBackPressed()) {
                    hasBackAction = true;
                }

                if(uiController.isAbortBackPressed()){
                    uiController.setAbortBackPressed(false);
                    break;
                }
            }
        }
        return hasBackAction;
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

    public interface FragmentLifecycle {
        void onCreateView(View view);

        void onDestroyView();
    }
}
