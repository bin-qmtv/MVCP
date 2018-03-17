package com.example.dell.myapplication.mvvmp;

import com.example.dell.myapplication.mvp.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Main2ViewModel extends BaseViewModel {

    public Observable<Object> fetch() {
        return Observable.empty()
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
