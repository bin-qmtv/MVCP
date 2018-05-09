package com.example.bin.myapplication.mvp;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * description
 *
 * @author bin
 * @date 2018/5/6 16:56
 */
public class PresenterDelegate implements InvocationHandler {

    private static final String TAG = "PresenterDelegate";
    private Object instance;

    private PresenterDelegate(@NonNull Object instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long time = SystemClock.elapsedRealtime();
        Object result = method.invoke(instance, args);
        long duration = SystemClock.elapsedRealtime() - time;
        if (method.getDeclaringClass() != Object.class) {
            Log.d(TAG, "invoke -> " + getMethodDesc(method)
                    + "(" + Arrays.toString(args) + ") : " + duration + "ms");
        }
        return result;
    }

    private String getMethodDesc(Method method) {
        Class cls = method.getDeclaringClass();
        return cls.getName() + "." + method.getName();
    }

    public static <T> T newProxy(@NonNull T presenter) {
        checkPresenter(presenter);
        return (T) Proxy.newProxyInstance(presenter.getClass().getClassLoader(),
                presenter.getClass().getInterfaces(), new PresenterDelegate(presenter));
    }

    private static <T> void checkPresenter(T presenter) {
        Class cls = presenter.getClass();
        Class[] clsInterfaces = cls.getInterfaces();
        if (clsInterfaces != null && clsInterfaces.length > 0) {
            Class superClass = cls.getSuperclass();
            if (superClass != null && !superClass.equals(Object.class) && !isLifecyclePresenter(presenter)) {
                throw new IllegalArgumentException("Do not extends other class except LifecyclePresenter");
            }
        }else {
            throw new IllegalArgumentException("Interfaces not found");
        }
    }

    private static <T> boolean isLifecyclePresenter(@NonNull T presenter) {
        return presenter instanceof LifecyclePresenter;
    }
}
