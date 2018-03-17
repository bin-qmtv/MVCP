package com.example.dell.myapplication.muiltmvvmp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.muiltmvvmp.btn.BtnController;
import com.example.dell.myapplication.muiltmvvmp.img.ImgController;
import com.example.dell.myapplication.muiltmvvmp.txt.TextViewModel;
import com.example.dell.myapplication.muiltmvvmp.txt.TxtController;
import com.example.dell.myapplication.mvp.ControllerActivity;
import com.example.dell.myapplication.mvp.UIController;

import java.util.Map;
import java.util.Set;

public class Main3Activity extends ControllerActivity {

    TextViewModel viewModel;
    final int CODE_TXTCONTROLLER = 1;

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
        addUIController(new TxtController(this));
        addUIController(new ImgController(this));
        addUIController(new BtnController(this));
        super.init();

        viewModel = ViewModelProviders.of(this).get(TextViewModel.class);

        fetch();
    }

    public void fetch() {
        viewModel.fetch(null).subscribe(s -> {
            Set<Map.Entry<Class, UIController>> set = controllerArrayMap.entrySet();
            for (Map.Entry<Class, UIController> classUIControllerEntry : set) {
                //classUIControllerEntry.getValue().onFirstLoad();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_TXTCONTROLLER) {
            getUIController(TxtController.class).onResult(requestCode, resultCode, data);
        }
        // else...
    }
}
