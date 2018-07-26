package com.example.bin.myapplication.mvcp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvcp.btn.BtnContract;
import com.example.bin.myapplication.mvcp.btn.BtnController;
import com.example.bin.myapplication.mvcp.img.ImgContract;
import com.example.bin.myapplication.mvcp.img.ImgController;
import com.example.bin.myapplication.mvcp.img.ImgControllerDelegate;
import com.example.bin.myapplication.mvcp.txt.TextViewModel;
import com.example.bin.myapplication.mvcp.txt.TxtContract;
import com.example.bin.myapplication.mvcp.txt.TxtController;
import com.example.bin.myapplication.mvcp.viewpager.PagerController;
import com.example.bin.myapplication.mvp.ControllerActivity;

public class Main3Activity extends ControllerActivity {

    TextViewModel viewModel;//可用于不同UIController之间数据共享，跟用于Fragment共享数据用法一样

    public static void start(Context context) {
        Intent starter = new Intent(context, Main3Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    public void init() {
        super.init();

        viewModel = ViewModelProviders.of(this).get(TextViewModel.class);

        fetch();
    }

    @Override
    public void initUIController() {
        addUIController(new TxtController(this), TxtContract.View.class);
        addUIController(new ImgControllerDelegate(this, new ImgController(this)), ImgContract.View.class);
        addUIController(new BtnController(this), BtnContract.View.class);
        addUIController(new PagerController(this));
    }

    public void fetch() {
        viewModel.fetch(null).subscribe(s -> {

        });
    }

}
