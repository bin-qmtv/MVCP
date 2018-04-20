package com.example.bin.myapplication.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.bin.myapplication.R;

import java.util.Map;
import java.util.Set;

/**
 * <p>一个方便的对话框，支持原生alert和自定义内容，可设置居中/顶部/左右和底部弹出</p>
   <pre>
        <code>
             AwesomeDialog.alert(this)
                        .setMessage("msg")
                        .setClickListener((dialog, which) -> {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                // TODO:
                            } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                                // TODO:
                            }
                        })
                        .build()
                        .show();
        </code>
        <code>
             AwesomeDialog.custom(this)
                        .setView(R.layout.dialog_awesome)
                        .setViewOnClickListener(R.id.btn, v -> {})
                        .build()
                        .show();
        </code>
        <code>
             AwesomeDialog.bottomSheet(this)
                        .setView(R.layout.dialog_awesome)
                        .setAdapter(R.id.recyclerView, adapter)
                        .setViewOnItemClickListener(R.id.recyclerView, onItemClickListener)
                        .build()
                        .show();
        </code>
   </pre>
 * <p>Need support version > 27.1</p>
 * @author bin
 * @date 2018/3/12 17:45
 */
public class AwesomeDialog extends AppCompatDialogFragment {

    private Builder builder;
    private int gravity;
    private int animations;
    private float dimAmount;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (builder != null) {
            this.gravity = builder.gravity;
            this.animations = builder.animations;
            this.dimAmount = builder.dimAmount;
            return builder.create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.dimAmount = dimAmount;
                if (animations != 0) params.windowAnimations = animations;
                window.setGravity(gravity);
            }
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (!manager.isStateSaved()) {
            super.show(manager, tag);
        }
    }

    @Override
    public void showNow(@NonNull FragmentManager manager, String tag) {
        if (!isAdded() && !manager.isStateSaved()) {
            super.showNow(manager, tag);
        }
    }

    public <T extends Dialog> void setBuilder(Builder<T> builder) {
        this.builder = builder;
    }

    public static abstract class Builder<T extends Dialog> {

        protected Context context;
        protected int theme;
        protected int iconRes;
        protected CharSequence title;
        protected CharSequence message;
        protected CharSequence[] items;
        protected CharSequence positiveButtonText;
        protected Drawable positiveButtonIcon;
        protected DialogInterface.OnClickListener positiveButtonListener;
        protected CharSequence negativeButtonText;
        protected Drawable negativeButtonIcon;
        protected DialogInterface.OnClickListener negativeButtonListener;
        protected CharSequence neutralButtonText;
        protected Drawable neutralButtonIcon;
        protected DialogInterface.OnClickListener neutralButtonListener;
        protected DialogInterface.OnCancelListener onCancelListener;
        protected DialogInterface.OnDismissListener onDismissListener;
        protected DialogInterface.OnKeyListener onKeyListener;
        protected DialogInterface.OnShowListener onShowListener;
        protected DialogInterface.OnClickListener onItemClickListener;
        protected ArrayMap<Integer, DialogInterface.OnClickListener> viewClickListeners;
        protected ArrayMap<Integer, OnItemClickListener> onItemClickListeners;
        protected ArrayMap<Integer, Pair<RecyclerView.Adapter, RecyclerView.LayoutManager>> listPair;
        protected boolean cancelable = true;
        protected boolean cancelableOnTouchOutside = true;
        protected View view;

        protected int gravity = Gravity.CENTER;
        protected int animations;
        protected float dimAmount = 0.6f;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setTheme(@StyleRes int theme) {
            this.theme = theme;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(@DrawableRes int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setItems(CharSequence[] items,
                DialogInterface.OnClickListener onItemClickListener) {
            this.items = items;
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setClickListener(final DialogInterface.OnClickListener listener) {
            positiveButtonListener = listener;
            negativeButtonListener = listener;
            neutralButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text,
                final DialogInterface.OnClickListener listener) {
            positiveButtonText = text;
            positiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButtonIcon(Drawable positiveButtonIcon) {
            this.positiveButtonIcon = positiveButtonIcon;
            return this;
        }

        public Builder setPositiveButtonText(CharSequence text) {
            positiveButtonText = text;
            return this;
        }

        public Builder setPositiveButtonListener(final DialogInterface.OnClickListener listener) {
            positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text,
                final DialogInterface.OnClickListener listener) {
            negativeButtonText = text;
            negativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButtonText(CharSequence negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public Builder setNegativeButtonIcon(Drawable negativeButtonIcon) {
            this.negativeButtonIcon = negativeButtonIcon;
            return this;
        }

        public Builder setNegativeButtonListener(
                DialogInterface.OnClickListener negativeButtonListener) {
            this.negativeButtonListener = negativeButtonListener;
            return this;
        }

        public Builder setNeutralButtonText(CharSequence neutralButtonText) {
            this.neutralButtonText = neutralButtonText;
            return this;
        }

        public Builder setNeutralButtonIcon(Drawable neutralButtonIcon) {
            this.neutralButtonIcon = neutralButtonIcon;
            return this;
        }

        public Builder setNeutralButtonListener(
                DialogInterface.OnClickListener neutralButtonListener) {
            this.neutralButtonListener = neutralButtonListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
            this.cancelableOnTouchOutside = cancelableOnTouchOutside;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.onKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public Builder setView(int viewResId) {
            view = View.inflate(context, viewResId, null);
            return this;
        }

        public Builder setViewOnClickListener(int viewId,
                DialogInterface.OnClickListener onClickListener) {
            if (viewClickListeners == null) {
                viewClickListeners = new ArrayMap<>();
            }
            viewClickListeners.put(viewId, onClickListener);
            return this;
        }

        public Builder setViewOnItemClickListener(int viewId,
                OnItemClickListener onItemClickListener) {
            if (onItemClickListeners == null) {
                onItemClickListeners = new ArrayMap<>();
            }
            onItemClickListeners.put(viewId, onItemClickListener);
            return this;
        }

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setAnimations(@StyleRes int animations) {
            this.animations = animations;
            return this;
        }

        public Builder setAdapter(int viewId, RecyclerView.Adapter adapter) {
            return setupList(viewId, adapter, null);
        }

        public Builder setupList(int viewId, RecyclerView.Adapter adapter,
                RecyclerView.LayoutManager layoutManager) {
            if (listPair == null) {
                listPair = new ArrayMap<>();
            }
            listPair.put(viewId, new Pair<>(adapter, layoutManager));
            return this;
        }

        public AlertDialog.Builder getAlertDialogBuilder(@StyleRes int theme) {
            return new AlertDialog.Builder(context, theme)
                    .setCancelable(cancelable)
                    .setOnCancelListener(onCancelListener)
                    .setOnDismissListener(onDismissListener)
                    .setOnKeyListener(onKeyListener);
        }

        protected void bindViewOnClickListener(final Dialog dialog) {
            if (viewClickListeners != null && view != null) {
                Set<Map.Entry<Integer, DialogInterface.OnClickListener>> set =
                        viewClickListeners.entrySet();
                for (Map.Entry<Integer, DialogInterface.OnClickListener> entry : set) {
                    if (entry != null && entry.getKey() != null) {
                        View clickView = view.findViewById(entry.getKey());
                        final DialogInterface.OnClickListener onClickListener = entry.getValue();
                        if (clickView != null && onClickListener != null) {
                            final int vid = clickView.getId();
                            clickView.setOnClickListener(v -> onClickListener.onClick(dialog, vid));
                        }
                    }
                }
            }
        }

        protected void bindList(final Dialog dialog) {
            if (view != null && listPair != null) {
                Set<Map.Entry<Integer, Pair<RecyclerView.Adapter, RecyclerView.LayoutManager>>>
                        adapterSet = listPair.entrySet();
                for (Map.Entry<Integer, Pair<RecyclerView.Adapter, RecyclerView.LayoutManager>>
                        entry : adapterSet) {
                    Integer viewId = entry.getKey();
                    if (viewId != null) {
                        View v = view.findViewById(viewId);
                        if (v instanceof RecyclerView) {
                            RecyclerView rv = (RecyclerView) v;
                            Pair<RecyclerView.Adapter, RecyclerView.LayoutManager> pair = entry.getValue();
                            RecyclerView.Adapter adapter = pair.first;
                            RecyclerView.LayoutManager layoutManager = pair.second;
                            if (adapter != null) {
                                rv.setAdapter(adapter);
                            }
                            if (layoutManager == null) {
                                layoutManager = new LinearLayoutManager(rv.getContext());
                            }
                            rv.setLayoutManager(layoutManager);

                            if (onItemClickListeners != null) {
                                final OnItemClickListener onItemClickListener = onItemClickListeners.get(viewId);
                                if (onItemClickListener != null) {
                                    rv.addOnItemTouchListener(new ItemTouchListener(rv,
                                            new ItemTouchListener.OnItemClickListener() {

                                                @Override
                                                public void onClick(RecyclerView.ViewHolder holder,
                                                        int position) {
                                                    onItemClickListener.onClick(dialog, holder, position);
                                                }

                                                @Override
                                                public boolean onDoubleClick(
                                                        RecyclerView.ViewHolder holder, int position) {
                                                    return onItemClickListener.onDoubleClick(dialog,
                                                            holder, position);
                                                }

                                                @Override
                                                public void onLongClick(
                                                        RecyclerView.ViewHolder holder, int position) {
                                                    onItemClickListener.onLongClick(dialog, holder,
                                                            position);
                                                }
                                            }));
                                }
                            }
                        }
                    }
                }
            }
        }

        public abstract T create();

        public AwesomeDialog build() {
            AwesomeDialog dialog = new AwesomeDialog();
            dialog.setBuilder(this);
            return dialog;
        }
    }

    public static class AlertBuilder extends Builder<AlertDialog> {

        AlertBuilder(@NonNull Context context) {
            super(context);
        }

        @Override
        public AlertDialog create() {
            if (TextUtils.isEmpty(positiveButtonText)) {
                positiveButtonText = "确定";
            }
            if (TextUtils.isEmpty(negativeButtonText)) {
                negativeButtonText = "取消";
            }
            if (items != null && items.length > 0 && onItemClickListener != null) {
                positiveButtonText = null;
                negativeButtonText = null;
                positiveButtonListener = null;
                negativeButtonListener = null;
            }
            if (theme == 0) theme = R.style.AwesomeDialogTheme;
            return getAlertDialogBuilder(theme)
                    .setTitle(title)
                    .setIcon(iconRes)
                    .setMessage(message)
                    .setItems(items, onItemClickListener)
                    .setPositiveButtonIcon(positiveButtonIcon)
                    .setNegativeButtonIcon(negativeButtonIcon)
                    .setNeutralButtonIcon(neutralButtonIcon)
                    .setPositiveButton(positiveButtonText, positiveButtonListener)
                    .setNegativeButton(negativeButtonText, negativeButtonListener)
                    .setNeutralButton(neutralButtonText, neutralButtonListener)
                    .create();
        }
    }

    public static class BottomSheetBuilder extends Builder<BottomSheetDialog> {

        private int peekHeight;
        private boolean isFullScreen;

        BottomSheetBuilder(Context context) {
            super(context);
        }

        public Builder setPeekHeight(int peekHeight) {
            this.peekHeight = peekHeight;
            return this;
        }

        public Builder setFullScreen(boolean isFullScreen) {
            this.isFullScreen = isFullScreen;
            return this;
        }

        @Override
        public BottomSheetDialog create() {
            BottomSheetDialog dialog = new BottomSheetDialog(context,
                    R.style.AwesomeBottomSheetDialog);
            dialog.setContentView(view);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
            dialog.setOnShowListener(dialog1 -> {
                if (view != null) {
                    ViewGroup viewParent = (ViewGroup) view.getParent();
                    if (isFullScreen) {
                        ViewGroup.LayoutParams params = viewParent.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        viewParent.setLayoutParams(params);
                    }
                    BottomSheetBehavior behavior = BottomSheetBehavior.from(viewParent);
                    int state = BottomSheetBehavior.STATE_EXPANDED;
                    if (peekHeight != 0) {
                        state = BottomSheetBehavior.STATE_COLLAPSED;
                    }
                    behavior.setSkipCollapsed(state != BottomSheetBehavior.STATE_COLLAPSED);
                    behavior.setState(state);
                    behavior.setPeekHeight(peekHeight);
                }
                if (onShowListener != null) {
                    onShowListener.onShow(dialog1);
                }
            });
            bindViewOnClickListener(dialog);
            bindList(dialog);
            return dialog;
        }
    }

    public static class CustomBuilder extends Builder<AlertDialog> {

        CustomBuilder(Context context) {
            super(context);
        }

        @Override
        public AlertDialog create() {
            if (theme == 0) theme = R.style.AwesomeDialogTheme_Transparent;
            AlertDialog.Builder builder = getAlertDialogBuilder(theme).setView(view);
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(onShowListener);
            bindViewOnClickListener(dialog);
            bindList(dialog);
            return dialog;
        }
    }

    /**
     * Optional title & a message & 2 buttons
     *
     * @param context Activity Context
     * @return AlertBuilder
     */
    public static AlertBuilder alert(Context context) {
        return new AlertBuilder(context);
    }

    /**
     * A custom sheet dialog alert from bottom
     *
     * @param context Activity Context
     * @return BottomSheetBuilder
     */
    public static BottomSheetBuilder bottomSheet(Context context) {
        return new BottomSheetBuilder(context);
    }

    /**
     * Set custom view
     *
     * @param context Activity Context
     * @return CustomBuilder
     */
    public static CustomBuilder custom(Context context) {
        return new CustomBuilder(context);
    }


    public interface OnItemClickListener {
        void onClick(Dialog dialog, RecyclerView.ViewHolder holder, int position);

        boolean onDoubleClick(Dialog dialog, RecyclerView.ViewHolder holder, int position);

        void onLongClick(Dialog dialog, RecyclerView.ViewHolder holder, int position);
    }

    public static class OnItemClickAdapter implements OnItemClickListener {

        @Override
        public void onClick(Dialog dialog, RecyclerView.ViewHolder holder, int position) {
        }

        @Override
        public boolean onDoubleClick(Dialog dialog, RecyclerView.ViewHolder holder, int position) {
            return false;
        }

        @Override
        public void onLongClick(Dialog dialog, RecyclerView.ViewHolder holder, int position) {
        }
    }
}