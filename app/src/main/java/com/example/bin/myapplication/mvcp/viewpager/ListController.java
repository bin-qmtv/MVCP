package com.example.bin.myapplication.mvcp.viewpager;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * @date 2018/4/13 11:16
 */
public class ListController extends UIController implements ControllerFragment.FragmentLifecycle {

    @BindView(R.id.list)
    RecyclerView list;

    private Unbinder unbinder;

    public ListController(ControllerFragment controller) {
        super(controller);
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
    public void initView() {
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new ListAdapter());
    }

    public static class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent,
                    false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            holder.tv.setText("label " + position);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tv = itemView.findViewById(R.id.tv);

        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
