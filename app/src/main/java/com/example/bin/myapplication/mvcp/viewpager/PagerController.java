package com.example.bin.myapplication.mvcp.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.example.bin.myapplication.R;
import com.example.bin.myapplication.mvp.ControllerActivity;
import com.example.bin.myapplication.mvp.UIController;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description
 *
 * @author bin
 * @date 2018/4/13 9:49
 */
public class PagerController extends UIController implements Serializable {

    @BindView(R.id.pager)
    ViewPager pager;

    public PagerController(ControllerActivity controller) {
        super(controller);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this, getControllerActivity());

        FragmentAdapter adapter = new FragmentAdapter(getControllerActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);

    }

    public static class FragmentAdapter extends FragmentStatePagerAdapter {

        SparseArray<Fragment> mFragments = new SparseArray<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragments.get(position);
            if (fragment == null) {
                switch (position) {
                    case 0:
                        fragment = new TestFragment();
                        break;
                    case 1:
                        fragment = new TestFragment2();
                        break;
                    case 2:
                        fragment = new TestFragment3();
                        break;
                    case 3:
                        fragment = new TestFragment4();
                        break;
                    default:
                        break;
                }
                mFragments.put(position, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
