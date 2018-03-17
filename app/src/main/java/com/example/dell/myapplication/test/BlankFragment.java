package com.example.dell.myapplication.test;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.example.dell.myapplication.R;
import com.example.dell.myapplication.mvp.MVPFragment;


public class BlankFragment extends MVPFragment<BlankContract.Presenter> implements BlankContract.View {

    private static final String ARG_PARAM = "param";

    private String mParam;

    public BlankFragment() {
    }

    public static BlankFragment newInstance(String param) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    protected void initPresenter() {
        new BlankPresenter(this, ViewModelProviders.of(this)
                .get(BlankViewModel.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_blank;
    }

    @Override
    public void initView() {

    }
}
