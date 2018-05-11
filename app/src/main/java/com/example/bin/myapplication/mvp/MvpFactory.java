package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * description
 *
 * @author bin
 * @date 2018/5/9 17:50
 */
public class MvpFactory {

    public static <T> T newInstance(@NonNull Class<?> cls, @NonNull Object... initargs) {
        checkInterfaces(cls);
        Constructor[] constructors = cls.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length == initargs.length) {
                try {
                    return (T) constructor.newInstance(initargs);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }else {
                throw new IllegalArgumentException(cls.getName() + " Constructor not match");
            }
        }
        return null;
    }

    public static <T> T newProxy(@NonNull T instance) {
        checkInterfaces(instance.getClass());
        return (T) Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), new MvpDelegate(instance));
    }

    static String getMethodDesc(Method method) {
        Class cls = method.getDeclaringClass();
        return cls.getName() + "." + method.getName();
    }

    static void checkInterfaces(Class<?> view) {
        Class[] interfaces = view.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            return;
        }
        throw new IllegalArgumentException(view.getName() + ": interfaces not found");
    }

}
