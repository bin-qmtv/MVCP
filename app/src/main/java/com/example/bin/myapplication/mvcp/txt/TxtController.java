package com.example.bin.myapplication.mvcp.txt;

import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;
import com.example.bin.myapplication.ui.StateView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * R.layout.activity_main3
 *
 * @author bin
 * @date 2018/3/15 18:37
 */
public class TxtController extends UIController<TxtContract.Presenter> implements TxtContract.View {

    private TextView text;
    private StateView mStateView;

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

    @Override
    public void loading(boolean b) {
        if (mStateView == null) {
            mStateView = new StateView(getContext()).attachTo(text);
        }

        mStateView.setState(b ? StateView.STATE_LOADING : StateView.STATE_NONE);
    }

    @Override
    public Observable<String> getTextCallBack() {
        return Observable.just(getText()).delay(1200, TimeUnit.MILLISECONDS);
    }

    private int i = 0;

    //@Priority(3)
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
