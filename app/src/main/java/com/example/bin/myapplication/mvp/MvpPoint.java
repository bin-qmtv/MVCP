package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;

/**
 * description
 *
 * @author bin
 * @date 2018/5/10 10:54
 */
public interface MvpPoint {

    void point(@NonNull PointParams pointParams);

    class PointParams {

        PointParams(Object[] params, String extra) {
            this.params = params;
            this.extra = extra;
        }

        public Object[] params;
        public String extra;
    }
}
