package com.example.bin.myapplication.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.bin.myapplication.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description
 *
 * @author bin
 * @date 2018/4/4 16:37
 */
public class StateView extends FrameLayout {

    public static final String TAG = "StateView";

    private boolean isAttach;

    @IntDef({STATE_NONE, STATE_LOADING, STATE_EMPTY, STATE_ERROR, STATE_OFFLINE})
    @Retention(RetentionPolicy.SOURCE)
    @interface State {
    }

    public static final int STATE_NONE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_OFFLINE = 4;

    private List<View> stateViews = new ArrayList<>();
    private Map<Integer, OnClickListener> viewClickListeners = new ArrayMap<>();

    private View loadingView;
    private View emptyView;
    private View errorView;
    private View offlineView;

    private int loadingViewResId = R.layout.state_loading;
    private int emptyViewResId = R.layout.state_empty;
    private int errorViewResId = R.layout.state_error;
    private int offlineViewResId = R.layout.state_offline;

    private boolean animate = true;
    private static final long DURATION = 150;
    private int state;
    private float z;

    public StateView(@NonNull Context context) {
        super(context);
        if (stateViews == null) stateViews = new ArrayList<>();
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        loadingViewResId = a.getResourceId(R.styleable.StateView_state_loadingView, loadingViewResId);
        emptyViewResId = a.getResourceId(R.styleable.StateView_state_emptyView, emptyViewResId);
        errorViewResId = a.getResourceId(R.styleable.StateView_state_errorView, errorViewResId);
        offlineViewResId = a.getResourceId(R.styleable.StateView_state_offlineView, offlineViewResId);
        state = a.getInt(R.styleable.StateView_state_viewState, STATE_NONE);
        animate = a.getBoolean(R.styleable.StateView_state_animateViewChanges, true);
        a.recycle();

        isAttach = true;
        if (stateViews == null) stateViews = new ArrayList<>();
        switch (state) {
            case STATE_LOADING:
                addLoadingView();
                break;
            case STATE_EMPTY:
                addEmptyView();
                break;
            case STATE_ERROR:
                addErrorView();
                break;
            case STATE_OFFLINE:
                addOfflineView();
                break;
            default:
                break;
        }
    }

    public StateView attachTo(@NonNull final View view) {
        return attachTo(view, null);
    }

    public StateView attachTo(@NonNull final View view, final OnStateClickListener onStateClickListener) {
        if (isAttach) {
            throw new UnsupportedOperationException("StateView is already attached");
        }

        ViewGroup viewParent = (ViewGroup) view.getParent();
        int index = viewParent.indexOfChild(view);
        ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            z = view.getZ();
            if (view instanceof Button) {
                int btnZid = Resources.getSystem().getIdentifier("button_pressed_z_material", "dimen", "android");
                if (btnZid != 0) {
                    z += getResources().getDimension(btnZid);
                }
            }
        }
        LayoutParams params = new LayoutParams(viewLayoutParams.width, viewLayoutParams.height);
        params.gravity = Gravity.CENTER;
        if (viewLayoutParams instanceof MarginLayoutParams) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) viewLayoutParams;
            params.leftMargin = layoutParams.leftMargin;
            params.rightMargin = layoutParams.rightMargin;
            params.topMargin = layoutParams.topMargin;
            params.bottomMargin = layoutParams.bottomMargin;
        }
        viewParent.removeView(view);
        addView(view, params);
        viewParent.addView(this, index, viewLayoutParams);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStateClickListener != null) {
                    onStateClickListener.onClick(v, getState());
                }
            }
        });

        isAttach = true;
        return this;
    }

    public boolean isAttach() {
        return isAttach;
    }

    public void detach() {
        ViewGroup viewParent = (ViewGroup) getParent();
        if (viewParent != null) {
            removeState();
            int index = viewParent.indexOfChild(this);
            View view = getChildAt(0);
            removeView(view);
            viewParent.addView(view, index);
            viewParent.removeView(this);
            isAttach = false;
        }
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public void setState(@State int state) {
        if (!isAttach) {
            Log.w(TAG, "StateView is not attach");
            return;
        }
        this.state = state;
        switch (state) {
            case STATE_NONE:
                removeState();
                break;
            case STATE_LOADING:
                setOnLoading();
                break;
            case STATE_EMPTY:
                setOnEmpty();
                break;
            case STATE_ERROR:
                setOnError();
                break;
            case STATE_OFFLINE:
                setOnOffline();
                break;
            default:
                break;
        }
    }

    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }

    public void setLoadingViewResId(int loadingViewResId) {
        this.loadingViewResId = loadingViewResId;
    }

    public void setEmptyViewResId(int emptyViewResId) {
        this.emptyViewResId = emptyViewResId;
    }

    public void setErrorViewResId(int errorViewResId) {
        this.errorViewResId = errorViewResId;
    }

    private void setOnLoading() {
        removeStateView(false);
        addLoadingView();
    }

    private void setOnEmpty() {
        removeStateView(false);
        addEmptyView();
    }

    private void setOnError() {
        removeStateView(false);
        addErrorView();
    }

    private void setOnOffline() {
        removeStateView(false);
        addOfflineView();
    }

    private void addOnClickListener(View view) {
        if (viewClickListeners != null && viewClickListeners.size() > 0) {
            Set<Map.Entry<Integer, OnClickListener>> set = viewClickListeners.entrySet();
            for (Map.Entry<Integer, OnClickListener> entry : set) {
                View v = view.findViewById(entry.getKey());
                if (v != null) v.setOnClickListener(entry.getValue());
            }
        }
    }

    private void addLoadingView() {
        if (loadingViewResId != 0) {
            loadingView = getInflater().inflate(loadingViewResId, this, false);
        }
        if (loadingView != null) {
            addStateView(loadingView);
        }
    }

    private void addEmptyView() {
        if (emptyViewResId != 0) {
            emptyView = getInflater().inflate(emptyViewResId, this, false);
        }
        if (emptyView != null) {
            addStateView(emptyView);
        }
    }

    private void addErrorView() {
        if (errorViewResId != 0) {
            errorView = getInflater().inflate(errorViewResId, this, false);
        }
        if (errorView != null) {
            addStateView(errorView);
        }
    }

    private void addOfflineView() {
        if (offlineViewResId != 0) {
            offlineView = getInflater().inflate(offlineViewResId, this, false);
        }
        if (offlineView != null) {
            addStateView(offlineView);
        }
    }

    private void addStateView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && z != 0) {
            view.setZ(z);
        }
        stateViews.add(view);
        addView(view);
        addOnClickListener(view);
        if (animate) {
            view.setAlpha(0);
            view.animate().alpha(1).setDuration(DURATION).setListener(null).start();
        }
    }

    private LayoutInflater getInflater() {
        return LayoutInflater.from(getContext());
    }

    void removeStateView(boolean animate) {
        if (stateViews != null) {
            for (final View stateView : stateViews) {
                if (stateView != null) {
                    if (animate) {
                        stateView.animate().alpha(0).setDuration(DURATION).setListener(
                                new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        stateView.animate().setListener(null);
                                        removeView(stateView);
                                    }
                                }).start();
                    } else {
                        removeView(stateView);
                    }
                }
            }
        }
    }

    void removeState() {
        removeStateView(animate);
        if (state != STATE_NONE) state = STATE_NONE;
    }

    public View getViewByState(@State int state) {
        switch (state) {
            case STATE_LOADING:
                return loadingView;
            case STATE_EMPTY:
                return emptyView;
            case STATE_ERROR:
                return errorView;
            case STATE_OFFLINE:
                return offlineView;
            case STATE_NONE:
            default:
                return null;
        }
    }

    public void setOnClickListenerById(@IdRes int id, OnClickListener onClickListener) {
        View view = findViewById(id);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        } else {
            viewClickListeners.put(id, onClickListener);
        }
    }

    public int getState() {
        return state;
    }

    public interface OnStateClickListener {
        void onClick(View view, int state);
    }

}