package com.example.bin.myapplication.mvcp.viewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerFragment;
import com.example.bin.myapplication.mvp.UIController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description
 *
 * @author bin
 * @date 2018/4/18 16:30
 */
public class TabController extends UIController implements ControllerFragment.FragmentLifecycle {

    @BindView(R.id.tab)
    TabLayout tab;

    private Unbinder unbinder;

    public TabController(ControllerFragment controller) {
        super(controller);
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

    @Override
    public void onSaveState(Bundle outState) {
        if (tab != null) {
            if (outState == null) {
                outState = new Bundle();
            }
            outState.putInt("pos", tab.getSelectedTabPosition());
        }
    }

    @Override
    public void onStateRestored(Bundle savedState) {
        if (savedState != null) {
            if(savedState.containsKey("pos")) {
                int pos = savedState.getInt("pos");
                TabLayout.Tab tabItem = tab.getTabAt(pos);
                if (tabItem != null) tabItem.select();
            }
        }
    }
}
