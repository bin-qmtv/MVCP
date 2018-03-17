package com.example.dell.myapplication.mvp;

/**
 * description
 *
 * @author bin
 * @date 2018/2/28 17:00
 */
public abstract class MVPActivity<P> extends BaseCleanActivity {

    protected P presenter;

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void init() {
        initPresenter();
        super.init();
    }

    protected abstract void initPresenter();

    public void showLoading(boolean cancelable){}

    public void showReload(boolean isShow){}

    public void cancelLoading(){}

    public void showError(boolean show){}

    public void showEmpty(boolean show){}
}
