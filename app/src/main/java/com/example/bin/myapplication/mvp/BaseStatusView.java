package com.example.bin.myapplication.mvp;

/**
 * description
 *
 * @author bin
 * @date 2018/3/1 16:30
 */
public interface BaseStatusView<P> extends BaseView<P> {
    void showLoading(boolean cancelable);

    void showReload(boolean isShow);

    void cancelLoading();

    void showError(boolean show);

    void showEmpty(boolean show);

    void showOffline(boolean show);
}