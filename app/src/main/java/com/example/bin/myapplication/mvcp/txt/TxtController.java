package com.example.bin.myapplication.mvcp.txt;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;

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
                .get(TextViewModel.class));
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
                        if (loading == 3) {
                            loading = 0;
                        }
                        StringBuilder sb = new StringBuilder("• ");
                        for (int i = 0; i < loading; i++) {
                            sb.append("• ");
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
        return Observable.just(getText()).delay(1200, TimeUnit.MILLISECONDS);
    }

    private int i = 0;
    @Override
    public boolean onBackPressed() {
        i++;
        if (i < 2){
            Toast.makeText(controller, "再按一次", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onBackPressed();
    }

    public void clear() {
        //释放资源
    }
}
