package com.example.bin.myapplication.mvp;

/**
 * description
 *
 * @author bin
 * @date 2018/6/13 17:26
 */
public interface OnBackPressedAction {

    /** 在UIController执行后退键之前的回调 */
    void beforeUIControllersBackPressed();

    /** 在系统真正执行后退键处理之前的回调 */
    void beforeSuperBackPressed();
}
