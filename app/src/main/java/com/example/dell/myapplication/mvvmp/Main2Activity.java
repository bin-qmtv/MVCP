package com.example.dell.myapplication.mvvmp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.mvp.MVPActivity;

public class Main2Activity extends MVPActivity<Main2Contract.Presenter> implements Main2Contract.View {

    private android.widget.TextView text;

    public static void start(Context context) {
        Intent starter = new Intent(context, Main2Activity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initPresenter() {
        new Main2Presenter(this, ViewModelProviders.of(this)
                .get(Main2ViewModel.class));
    }

    @Override
    public void initView() {
        initToolBar(findViewById(R.id.toolbar), "mvp demo");
        text = findViewById(R.id.text);
    }

    public void clickBtn(View view) {
        presenter.doSomething();
    }

    @Override
    public void setText(String txt) {
        this.text.setText(txt);
    }
}
