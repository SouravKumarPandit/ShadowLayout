package com.pandit.sourav.shadowlayout.temp;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class BaseGroup extends FrameLayout {

    public abstract void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs);

    protected int mViewWidth;
    protected int mViewHeight;
    protected Animator animator;

    public BaseGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCustomView(context, attrs);
        initAnimator();
    }

    public BaseGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomView(context, attrs);
        initAnimator();
    }

    public void initAnimator() {
    }

    public Animator getAnimator() {
        return animator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        initAnimator();
    }
}