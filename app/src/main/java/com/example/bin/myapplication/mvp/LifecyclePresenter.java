package com.example.bin.myapplication.mvp;

import android.arch.lifecycle.LifecycleOwner;

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
    private CompositeDisposable disposables = new CompositeDisposable();

    public LifecyclePresenter(V view) {
        if (view instanceof LifecycleOwner) {
            register((LifecycleOwner) view);
        }
        this.view = view;
        view.setPresenter(this);
    }

    void addDisposable(Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    void register(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
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