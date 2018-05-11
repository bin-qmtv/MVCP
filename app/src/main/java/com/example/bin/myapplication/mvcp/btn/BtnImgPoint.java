package com.example.bin.myapplication.mvcp.btn;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bin.myapplication.mvp.MvpPoint;

import java.util.Arrays;

/**
 * description
 *
 * @author bin
 * @date 2018/5/10 11:41
 */
public class BtnImgPoint implements MvpPoint{

    @Override
    public void point(@NonNull PointParams pointParams) {
        Log.d("MvpDelegate", "point : " + Arrays.toString(pointParams.params) + ", extra:" + pointParams.extra);
    }
}
