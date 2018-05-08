package com.example.bin.myapplication.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.Disposable;

/**
 * description
 *
 * @author bin
 * @date 2018/5/7 16:27
 */
public interface ILifecycle extends LifecycleObserver {

    void addDisposable(Disposable disposable);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy();
}
