package com.example.bin.myapplication.mvcp.viewpager;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerFragment;

/**
 * description
 *
 * @author bin
 * @date 2018/4/13 10:06
 */
public class TestFragment4 extends ControllerFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test2;
    }

    @Override
    public void initUIController() {
        addUIController(new TabController(this));
    }
}
