package com.example.dell.myapplication.muiltmvvmp.txt;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TextRepository {
    private static TextRepository mInstance;

    private TextRepository() {
    }

    public static TextRepository getInstance() {
        if (mInstance == null) {
            synchronized (TextRepository.class) {
                if (mInstance == null) {
                    mInstance = new TextRepository();
                }
            }
        }
        return mInstance;
    }

    public Observable<String> fetch(Map<String, String> params) {
        return Observable.just("这是一个及其复杂的页面").delay(1800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
