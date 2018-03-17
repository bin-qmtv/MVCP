package com.example.dell.myapplication.muiltmvvmp.txt;

import com.example.dell.myapplication.mvp.BaseViewModel;

import java.util.Map;

import io.reactivex.Observable;

public class TextViewModel extends BaseViewModel {

    public Observable<String> fetch(Map<String, String> params) {
        return TextRepository.getInstance().fetch(params)
                .doOnSubscribe(this::add);
    }
}