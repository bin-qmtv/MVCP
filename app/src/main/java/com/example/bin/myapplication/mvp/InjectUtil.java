package com.example.bin.myapplication.mvp;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * description
 *
 * @author bin
 * @date 2018/5/9 10:45
 */
public class InjectUtil {

    private static final String TAG = "InjectUtil";

    public static <View extends BaseView> void injectPresenter(@NonNull View view) {
        Presenter presenterAnnotation = view.getClass().getAnnotation(Presenter.class);
        if (presenterAnnotation != null) {
            long time = SystemClock.elapsedRealtime();
            Class cls = presenterAnnotation.value();
            Constructor[] constructors = cls.getConstructors();
            for (Constructor constructor : constructors) {
                Class[] paramTypes = constructor.getParameterTypes();
                if (paramTypes.length == 1) {
                    try {
                        constructor.newInstance(view);
                        long duration = SystemClock.elapsedRealtime() - time;
                        Log.d(TAG, "injectPresenter: duration=" + duration + "ms");
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }else {
                    throw new IllegalArgumentException("Constructor params count < 2");
                }
            }
        }
    }
}
