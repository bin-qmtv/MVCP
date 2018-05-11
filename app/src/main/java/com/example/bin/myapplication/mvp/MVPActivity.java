package com.example.bin.myapplication.mvp;

import android.arch.lifecycle.LifecycleObserver;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 17:00
 */
public abstract class MVPActivity<P> extends BaseCleanActivity {

    protected P presenter;

    public void setPresenter(P presenter) {
        this.presenter = MvpFactory.newProxy(presenter);
        if (presenter instanceof LifecycleObserver) {
            LifecycleObserver lifecycleObserver = (LifecycleObserver) presenter;
            getLifecycle().addObserver(lifecycleObserver);
        }
    }

    @Override
    protected void init() {
        initPresenter();
        super.init();
    }

    protected void initPresenter(){
        if (this instanceof BaseView) {
            InjectUtil.injectPresenter((BaseView)this);
        }
    }

    public void showLoading(boolean cancelable){}

    public void showReload(boolean isShow){}

    public void cancelLoading(){}

    public void showError(boolean show){}

    public void showEmpty(boolean show){}

    public void showOffline(boolean show){}
}
