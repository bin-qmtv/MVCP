package com.example.bin.myapplication.ui;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * description
 *
 * @author bin
 * @date 2018/1/26 14:57
 */
public class ItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetector gestureDetector;

    public ItemTouchListener(final RecyclerView rv, final OnItemClickListener onItemClickListener) {
        gestureDetector = new GestureDetector(rv.getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder holder = rv.getChildViewHolder(child);
                    int position = rv.getChildAdapterPosition(child);
                    if (onItemClickListener != null) {
                        onItemClickListener.onLongClick(holder, position);
                    }
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder holder = rv.getChildViewHolder(child);
                    int position = rv.getChildAdapterPosition(child);
                    if (onItemClickListener != null && !onItemClickListener.onDoubleClick(holder, position)) {
                        onItemClickListener.onClick(holder, position);
                    }
                }
                return true;
            }


            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder holder = rv.getChildViewHolder(child);
                    int position = rv.getChildAdapterPosition(child);
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(holder, position);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    public interface OnItemClickListener {
        void onClick(RecyclerView.ViewHolder holder, int position);

        boolean onDoubleClick(RecyclerView.ViewHolder holder, int position);

        void onLongClick(RecyclerView.ViewHolder holder, int position);
    }

    public static abstract class OnItemClickAdapter implements OnItemClickListener {

        @Override
        public abstract void onClick(RecyclerView.ViewHolder holder, int position);

        @Override
        public boolean onDoubleClick(RecyclerView.ViewHolder holder, int position) {
            return false;
        }

        @Override
        public void onLongClick(RecyclerView.ViewHolder holder, int position) {
        }
    }
}
