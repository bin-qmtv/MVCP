package com.example.bin.myapplication.mvp;

/**
 * description
 *
 * @author bin
 * @date 2018/3/16 16:49
 */
public interface Controller {

    <T extends UIController> T getUIController(Class<T> cls);

    <T extends UIController> void addUIController (T t);
}
