package com.example.bin.myapplication.mvp;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
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

    private void checkPointBefore(Method method, Object[] args) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        PointBefore pointBefore = method.getAnnotation(PointBefore.class);
        if (pointBefore != null) {
            Class[] classes = pointBefore.value();
            String[] pointNames = pointBefore.name();
            String extra = pointBefore.extra();
            if (classes.length > 0) {
                for (Class cls : classes) {
                    invokePoint(args, cls, extra);
                }
            }
            if (pointNames.length > 0) {
                for (String pointName : pointNames) {
                    invokePoint(args, pointName, extra);
                }
            }
        }
    }

    private void checkPointAfter(Method method, Object[] args)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            InvocationTargetException {
        PointAfter pointAfter = method.getAnnotation(PointAfter.class);
        if (pointAfter != null) {
            Class[] classes = pointAfter.value();
            String[] pointNames = pointAfter.name();
            String extra = pointAfter.extra();
            if (classes.length > 0) {
                for (Class cls : classes) {
                    invokePoint(args, cls, extra);
                }
            }
            if (pointNames.length > 0) {
                for (String pointName : pointNames) {
                    invokePoint(args, pointName, extra);
                }
            }
        }
    }

    private void invokePoint(Object[] args, Class cls, String extra) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Class[] interfaces = cls.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class i : interfaces) {
                if (i.equals(MvpPoint.class)) {
                    Object point = getPoint(i.getCanonicalName());
                    if (point == null) {
                        if (cls != Object.class) {
                            point = cls.newInstance();
                            if (point != null) {
                                addPoint(point);
                            }
                        }
                    }
                    Method[] methods = i.getMethods();
                    if (methods != null && methods.length > 0) {
                        invokePointMethod(args, point, methods[0], extra);
                    }
                    break;
                }
            }
        }
    }

    private void invokePoint(Object[] args, String pointName, String extra) throws IllegalAccessException,
            ClassNotFoundException, InvocationTargetException {
        if (TextUtils.isEmpty(pointName)) return;
        Object point = getPoint(pointName);
        if (point == null) {
            point = Class.forName(pointName);
            if (point != null) {
                addPoint(point);
            }
        }
        if (point != null) {
            Class[] interfaces = point.getClass().getInterfaces();
            if (interfaces != null && interfaces.length > 0) {
                for (Class i : interfaces) {
                    if (i.equals(MvpPoint.class)) {
                        Method[] methods = i.getMethods();
                        if (methods != null && methods.length > 0) {
                            invokePointMethod(args, point, methods[0], extra);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void invokePointMethod(Object[] args, Object point, Method method, String extra)
            throws IllegalAccessException, InvocationTargetException {
        if (point != null && method != null) {
            MvpPoint.PointParams pp = new MvpPoint.PointParams(args, extra);
            method.invoke(point, pp);
        }
    }

    private void addPoint(@NonNull Object instance) {
        if (points == null) {
            points = new ArrayMap<>();
        }
        points.put(instance.getClass().getCanonicalName(), instance);
    }

    @Nullable
    private Object getPoint(@NonNull String key) {
        if (points != null) {
            return points.get(key);
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
