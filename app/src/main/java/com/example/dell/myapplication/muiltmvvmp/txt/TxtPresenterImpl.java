package com.example.dell.myapplication.muiltmvvmp.txt;

import android.util.Log;

import com.example.dell.myapplication.mvp.LifecyclePresenter;
import com.example.dell.myapplication.mvp.PresenterLifecycleObserver;

/**
 * description
 *
 * @author bin
 * @date 2018/3/15 19:06
 */
public class TxtPresenterImpl extends LifecyclePresenter<TxtContract.View> implements TxtContract.Presenter {

    private TextViewModel viewModel;
    String TAG = "TxtPresenterImpl";

    public TxtPresenterImpl(TxtContract.View view, TextViewModel viewModel) {
        super(view);
        this.viewModel = viewModel;
        Log.d(TAG, "TxtPresenterImpl: ");
    }

    @Override
    public void create() {
        doSomething();
    }

    @Override
    public void destroy() {
        super.destroy();
        // release resource
        view.clear();
    }

    @Override
    public void doSomething() {
        viewModel.fetch(null).subscribe(new PresenterLifecycleObserver<String>(this){

            @Override
            protected void onStart() {
                Log.d(TAG, "onStart: ");
                view.loading(true);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: ");
                view.loading(false);
                view.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                view.loading(false);
            }
        });
    }
}
