package com.example.dell.myapplication.muiltmvvmp.txt;

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

    public TxtPresenterImpl(TxtContract.View view, TextViewModel viewModel) {
        super(view);
        this.viewModel = viewModel;
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
                view.loading(true);
            }

            @Override
            public void onNext(String s) {
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
