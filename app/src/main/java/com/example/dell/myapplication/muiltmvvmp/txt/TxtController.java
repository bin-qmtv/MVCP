package com.example.dell.myapplication.muiltmvvmp.txt;

import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.widget.TextView;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.muiltmvvmp.Main3Activity;
import com.example.dell.myapplication.mvp.ControllerActivity;
import com.example.dell.myapplication.mvp.UIController;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * R.layout.activity_main3
 *
 * @author bin
 * @date 2018/3/15 18:37
 */
public class TxtController extends UIController<TxtContract.Presenter> implements TxtContract.View {

    private TextView text;
    private Disposable disposable;

    public TxtController(ControllerActivity controller) {
        super(controller);
    }

    @Override
    public void initPresenter() {
        new TxtPresenterImpl(this, ViewModelProviders.of(controller)
                .get(TextViewModel.class)).register(controller);
    }

    @Override
    public void initView() {
        text = findViewById(R.id.text);
    }

    @Override
    public String getText() {
        return text.getText().toString();
    }

    @Override
    public void setText(String txt) {
        text.setText(txt);
    }

    int loading = 0;

    @Override
    public void loading(boolean b) {

        // 模拟loading
        if (disposable == null || disposable.isDisposed()) {
            disposable = Observable.interval(250, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        Log.d("---", "loading: " + loading);
                        if (loading == 3) {
                            loading = 0;
                        }
                        StringBuilder sb = new StringBuilder("● ");
                        for (int i = 0; i < loading; i++) {
                            sb.append("● ");
                        }
                        setText(sb.toString());
                        loading++;
                    });
        }
        if (!b) {
            disposable.dispose();
        }
    }

    @Override
    public Observable<String> getTextCallBack() {
        return Observable.just(getText()).delay(1800, TimeUnit.MILLISECONDS);
    }

    public void clear() {
        //释放资源
    }
}
