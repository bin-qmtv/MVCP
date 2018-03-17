package com.example.dell.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.dell.myapplication.mvvmp.Main2Activity;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    CharSequence[] items = new CharSequence[]{"a", "b", "c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {


    }

    public void clickBtn(View view) {
        AwesomeDialog.custom(this)
                .setView(R.layout.dialog_awesome)
                .setViewOnClickListener(R.id.btn_test, (dialog, which) -> {
                    dialog.dismiss();
                    Main2Activity.start(this);
                })
                .create()
                .show();
    }
}