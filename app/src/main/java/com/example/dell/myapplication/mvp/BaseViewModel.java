package com.example.dell.myapplication.mvp;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description
 *
 * @author bin
 * @date 2017/11/30 18:22
 */
public class BaseViewModel extends ViewModel {

    protected static final int RETRYCOUNT = 3;
    private CompositeDisposable disposables = new CompositeDisposable();

    public void add(Object o) {
        if (o instanceof Disposable) {
            addDisposable((Disposable) o);
        } else {
            throw new IllegalArgumentException("参数有误,请传入可取消的订阅者");
        }
    }

    private void addDisposable(Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        if (disposables != null) {
            disposables.clear();
        }
    }
}
