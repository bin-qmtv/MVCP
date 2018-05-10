package com.example.bin.myapplication.mvp;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.bin.myapplication.mvp.annotation.PointAfter;
import com.example.bin.myapplication.mvp.annotation.PointBefore;
import com.example.bin.myapplication.mvp.annotation.PrintDuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

/**
 * description
 *
 * @author bin
 * @date 2018/5/10 10:25
 */
public class MvpDelegate implements InvocationHandler {

    private static final String TAG = "MvpDelegate";
    private Object instance;
    private Map<String, Object> points;

    private MvpDelegate(@NonNull Object instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        checkPointBefore(method, args);
        long time = SystemClock.elapsedRealtime();
        Object result = method.invoke(instance, args);
        checkPrintDuration(method, args, time);
        checkPointAfter(method, args);
        return result;
    }

    private void checkPointBefore(Method method, Object[] args)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            InvocationTargetException {
        PointBefore pointBefore = method.getAnnotation(PointBefore.class);
        if (pointBefore != null) {
            Class cls = pointBefore.value();
            String pointName = pointBefore.name();
            invokePoint(args, cls, pointName);
        }
    }

    private void checkPointAfter(Method method, Object[] args)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            InvocationTargetException {
        PointAfter pointAfter = method.getAnnotation(PointAfter.class);
        if (pointAfter != null) {
            Class cls = pointAfter.value();
            String pointName = pointAfter.name();
            invokePoint(args, cls, pointName);
        }
    }

    private void invokePoint(Object[] args, Class cls, String pointName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            InvocationTargetException {
        Class[] interfaces = cls.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class i : interfaces) {
                if (i.equals(MvpPoint.class)) {
                    Object point = getPoint(i);
                    if (point == null) {
                        if (cls != Object.class) {
                            point = cls.newInstance();
                        } else {
                            point = Class.forName(pointName);
                        }
                        if (point != null) {
                            addPoint(point);
                        }
                    }

                    if (point != null) {
                        Method[] methods = i.getMethods();
                        if (methods != null && methods.length > 0) {
                            MvpPoint.PointParams pp = new MvpPoint.PointParams(args);
                            methods[0].invoke(point, pp);
                        }
                    }
                    break;
                }
            }
        }
    }

    private void addPoint(@NonNull Object instance) {
        if (points == null) {
            points = new ArrayMap<>();
        }
        points.put(instance.getClass().getCanonicalName(), instance);
    }

    @Nullable
    private Object getPoint(Class i) {
        if (points != null) {
            return points.get(i.getCanonicalName());
        }
        return null;
    }

    private void checkPrintDuration(Method method, Object[] args, long time) {
        PrintDuration logDuration = method.getAnnotation(PrintDuration.class);
        if (logDuration != null) {
            long duration = SystemClock.elapsedRealtime() - time;
            Log.d(TAG, "invoke -> " + MvpFactory.getMethodDesc(method)
                    + "(" + Arrays.toString(args) + ") : " + duration + "ms");
        }
    }

    public static <T> T newProxy(@NonNull T instance) {
        check(instance);
        return (T) Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), new MvpDelegate(instance));
    }

    private static <T> void check(T instance) {
        Class cls = instance.getClass();
        MvpFactory.checkInterfaces(cls);
    }
}
