package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.bin.myapplication.mvp.annotation.Presenter;

/**
 * description
 *
 * @author bin
 * @date 2018/5/9 10:45
 */
public class InjectUtil {

    public static <View extends BaseView> void injectPresenter(@NonNull View view) {
        Presenter presenterAnnotation = view.getClass().getAnnotation(Presenter.class);
        if (presenterAnnotation != null) {
            Class cls = presenterAnnotation.value();
            MvpFactory.newInstance(cls, view);
        }
    }

    @Nullable
    static Class findBaseView(Class cls) {
        if (cls != null) {
            Class baseView = cls;
            Class[] interfaces = cls.getInterfaces();
            if (interfaces != null && interfaces.length > 0) {
                boolean isBaseView = false;
                for (Class ic : interfaces) {
                    if (ic != null && ic.equals(BaseView.class)) {
                        isBaseView = true;
                    }
                }
                if(isBaseView){
                    return baseView;
                }else {
                    for (Class ic : interfaces) {
                        baseView = findBaseView(ic);
                        if (baseView != null) {
                            return baseView;
                        }
                    }
                }
            }
        }
        return null;
    }
}
