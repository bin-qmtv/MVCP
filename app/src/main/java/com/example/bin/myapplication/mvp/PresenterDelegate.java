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

    public PresenterDelegate(@NonNull Object instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long time = SystemClock.elapsedRealtime();
        Object result = method.invoke(instance, args);
        long duration = SystemClock.elapsedRealtime() - time;
        Class cls = method.getDeclaringClass();
        if (cls != Object.class) {
            String sb = "invoke -> " + cls.getName() + "." + method.getName()
                    + "(" + Arrays.toString(args) + ") : " + duration + "ms";
            Log.d(TAG, sb);
        }
        return result;
    }

    public static <T> T newProxy(@NonNull Object presenter) {
        return (T) Proxy.newProxyInstance(presenter.getClass().getClassLoader(),
                presenter.getClass().getInterfaces(), new PresenterDelegate(presenter));
    }
}
