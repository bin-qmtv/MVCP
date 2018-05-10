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
        PointParams(Object[] params) {
            this.params = params;
        }

        public Object[] params;
    }
}
