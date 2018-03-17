package com.example.dell.myapplication.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 16:00
 */
public class LifecyclePresenter<V extends BaseView> implements LifecycleObserver {

    protected V view;
    private CompositeDisposable disposables = new CompositeDisposable();

    public LifecyclePresenter(V view) {
        if (view instanceof LifecycleOwner) {
            register((LifecycleOwner) view);
        }
        this.view = view;
        view.setPresenter(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        if (disposables != null) {
            disposables.clear();
        }
    }

    void addDisposable(Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    public void register(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
    }
}