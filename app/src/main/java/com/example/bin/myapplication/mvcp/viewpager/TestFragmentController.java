package com.example.bin.myapplication.mvcp.viewpager;

import android.view.View;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerFragment;
import com.example.bin.myapplication.mvp.UIController;
import com.example.bin.myapplication.ui.AwesomeDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * description
 *
 * @author bin
 * @date 2018/4/20 10:52
 */
public class TestFragmentController extends UIController implements ControllerFragment.FragmentLifecycle{

    private Unbinder unbinder;

    public TestFragmentController(ControllerFragment controller) {
        super(controller);
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void onCreateView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
    }

    @OnClick(R.id.btn_fragment1) void showAlert() {
        AwesomeDialog.alert(getContext())
                .setMessage("Awesome Fragment Controller")
                .create()
                .show();
    }
}
