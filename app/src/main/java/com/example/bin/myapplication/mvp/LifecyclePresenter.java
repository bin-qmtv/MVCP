package com.example.bin.myapplication.mvp;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 16:00
 */
public class LifecyclePresenter<V extends BaseView> implements ILifecycle {

    protected V view;
    private CompositeDisposable disposables;

    public LifecyclePresenter(@NonNull V view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void addDisposable(@NonNull Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    @Override
    public void create() {
    }

    @Override
    public void start() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void destroy() {
        if (disposables != null) {
            disposables.clear();
        }
    }
}