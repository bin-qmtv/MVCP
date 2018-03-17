package com.example.dell.myapplication.mvvmp;

import android.content.DialogInterface;
import android.widget.Toast;

import com.example.dell.myapplication.AwesomeDialog;
import com.example.dell.myapplication.muiltmvvmp.Main3Activity;
import com.example.dell.myapplication.mvp.LifecyclePresenter;

public class Main2Presenter extends LifecyclePresenter<Main2Contract.View> implements Main2Contract.Presenter {

    private Main2ViewModel viewModel;

    public Main2Presenter(Main2Contract.View view, Main2ViewModel viewModel) {
        super(view);
        this.viewModel = viewModel;
    }

    @Override
    public void create() {
        // Activity onCreate时自动调用   Now -> start to fly
        viewModel.fetch()
                .doOnComplete(() -> {
                    Toast.makeText(view.getContext().getApplicationContext()
                            , "fetch data complete", Toast.LENGTH_SHORT).show();
                    view.setText("fetch data complete");
                })
                .subscribe();
    }

    @Override
    public void doSomething() {
        AwesomeDialog.alert(view.getContext())
                .setMessage("要看更复杂的页面?")
                .setClickListener((dialog, which) -> {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        Main3Activity.start(view.getContext());
                    }
                })
                .create()
                .show();
    }
}
