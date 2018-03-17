package com.example.dell.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.Map;
import java.util.Set;

/**
 * <p>一个方便的对话框，支持原生alert和自定义内容</p>
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
                        .create()
                        .show();
        </code>
        <code>
             AwesomeDialog.custom(this)
                        .setView(R.layout.dialog_awesome)
                        .setViewOnClickListener(R.id.btn, v -> {})
                        .create()
                        .show();
        </code>
   </pre>
 *
 * @author bin
 * @date 2018/3/12 17:45
 */
public class AwesomeDialog {

    public static abstract class Builder<T extends Dialog> {

        public Context context;
        public int theme;
        public CharSequence title;
        public CharSequence message;
        public CharSequence[] items;
        public CharSequence positiveButtonText;
        public Drawable positiveButtonIcon;
        public DialogInterface.OnClickListener positiveButtonListener;
        public CharSequence negativeButtonText;
        public Drawable negativeButtonIcon;
        public DialogInterface.OnClickListener negativeButtonListener;
        public CharSequence neutralButtonText;
        public Drawable neutralButtonIcon;
        public DialogInterface.OnClickListener neutralButtonListener;
        public boolean cancelable = true;
        public DialogInterface.OnCancelListener onCancelListener;
        public DialogInterface.OnDismissListener onDismissListener;
        public DialogInterface.OnKeyListener onKeyListener;
        public DialogInterface.OnClickListener onItemClickListener;
        public View view;
        public ArrayMap<Integer, DialogInterface.OnClickListener> viewClickListeners;

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

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public void setItems(CharSequence[] items, DialogInterface.OnClickListener onItemClickListener) {
            this.items = items;
            this.onItemClickListener = onItemClickListener;
        }

        public Builder setClickListener(final DialogInterface.OnClickListener listener) {
            positiveButtonListener = listener;
            negativeButtonListener = listener;
            neutralButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, final DialogInterface.OnClickListener listener) {
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

        public Builder setNegativeButton(CharSequence text, final DialogInterface.OnClickListener listener) {
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

        public Builder setNegativeButtonListener(DialogInterface.OnClickListener negativeButtonListener) {
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

        public Builder setNeutralButtonListener(DialogInterface.OnClickListener neutralButtonListener) {
            this.neutralButtonListener = neutralButtonListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
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

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public Builder setView(int viewResId) {
            view = View.inflate(context, viewResId, null);
            return this;
        }

        public Builder setViewOnClickListener(int viewId, DialogInterface.OnClickListener onClickListener) {
            if (viewClickListeners == null) {
                viewClickListeners = new ArrayMap<>();
            }
            viewClickListeners.put(viewId, onClickListener);
            return this;
        }

        public AlertDialog.Builder getAlertDialogBuilder(@StyleRes int theme) {
            return new AlertDialog.Builder(context, theme)
                    .setCancelable(cancelable)
                    .setOnCancelListener(onCancelListener)
                    .setOnDismissListener(onDismissListener)
                    .setOnKeyListener(onKeyListener);
        }

        public abstract T create();
    }

    public static class AlertBuilder extends Builder<AlertDialog> {

        public AlertBuilder(@NonNull Context context) {
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
            if (theme == 0) theme = R.style.DialogTheme;
            return getAlertDialogBuilder(theme)
                    .setTitle(title)
                    .setMessage(message)
                    .setItems(items, onItemClickListener)
                    .setPositiveButton(positiveButtonText, positiveButtonListener)
                    .setNegativeButton(negativeButtonText, negativeButtonListener)
                    .create();
        }
    }

    public static class CustomBuilder extends Builder<AlertDialog> {

        public CustomBuilder(Context context) {
            super(context);
        }

        @Override
        public AlertDialog create() {
            if (theme == 0) theme = R.style.DialogTheme_Transparent;
            AlertDialog.Builder builder = getAlertDialogBuilder(theme).setView(view);
            AlertDialog dialog = builder.create();
            if (viewClickListeners != null && view != null) {
                Set<Map.Entry<Integer, DialogInterface.OnClickListener>> set = viewClickListeners.entrySet();
                for (Map.Entry<Integer, DialogInterface.OnClickListener> entry : set) {
                    if (entry != null && entry.getKey() != null) {
                        view.findViewById(entry.getKey())
                                .setOnClickListener(v -> entry.getValue().onClick(dialog, v.getId()));
                    }
                }
            }
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
     * Set custom view
     *
     * @param context Activity Context
     * @return CustomBuilder
     */
    public static CustomBuilder custom(Context context) {
        return new CustomBuilder(context);
    }
}
