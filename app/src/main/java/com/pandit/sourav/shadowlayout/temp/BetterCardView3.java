package com.pandit.sourav.shadowlayout.temp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pandit.sourav.shadowlayout.R;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: ShadowLayout.java
 * Author: Better
 * Create: 2018/1/19 19:05
 * <p>
 * Changes (from 2018/1/19)
 * -----------------------------------------------------------------
 * 2018/1/19 : Create ShadowLayout.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BetterCardView3 extends BaseGroup {

    private int SIZE_UNSET;

    private Drawable foregroundDraw;
    private Rect selfBounds = new Rect();
    private Rect overlayBounds = new Rect();
    private int shadowColor;
    private int foregroundColor;
    private int backgroundColor;
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;

    private float cornerRadiusTL;
    private float cornerRadiusTR;
    private float cornerRadiusBL;
    private float cornerRadiusBR;

    private Paint paint;

    private float shadowElevationTop;
    private float shadowElevationLeft;
    private float shadowElevationRight;
    private float shadowElevationBottom;

    private Paint mCornerShadowPaint;
    private Paint mEdgeShadowPaint;


    public BetterCardView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterCardView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        this.setMeasuredDimension(FrameLayout.getDefaultSize(0, widthMeasureSpec), FrameLayout.getDefaultSize(0, heightMeasureSpec));
        boolean shadowMeasureWidthMatchParent = this.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT;
        boolean shadowMeasureHeightMatchParent = this.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT;
        int widthSpec = widthMeasureSpec;
        int heightSpec;
        if (shadowMeasureWidthMatchParent) {
            heightSpec = this.getMeasuredWidth() - (int) shadowElevationRight - (int) shadowElevationLeft;
            widthSpec = MeasureSpec.makeMeasureSpec(heightSpec, MeasureSpec.EXACTLY);
        }

        heightSpec = heightMeasureSpec;
        if (shadowMeasureHeightMatchParent) {
            int childHeightSize = this.getMeasuredHeight() - (int) shadowElevationTop - (int) shadowElevationBottom;
            heightSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        }

        View child = this.getChildAt(0);
        if (child != null && child.getVisibility() != View.GONE) {
            this.measureChildWithMargins(child, widthSpec, 0, heightSpec, 0);
            MarginLayoutParams var10000 = (MarginLayoutParams) child.getLayoutParams();
            if (var10000 == null) {
                return;
            }

            MarginLayoutParams lp = (MarginLayoutParams) var10000;
            maxWidth = shadowMeasureWidthMatchParent ? Math.max(maxWidth, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin) : (int) Math.max(maxWidth, child.getMeasuredWidth() + this.shadowElevationLeft + this.shadowElevationRight + lp.leftMargin + lp.rightMargin);
            maxHeight = shadowMeasureHeightMatchParent ? Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin) : (int) Math.max(maxHeight, child.getMeasuredHeight() + this.shadowElevationTop + this.shadowElevationBottom + lp.topMargin + lp.bottomMargin);
            childState = View.combineMeasuredStates(childState, child.getMeasuredState());
        }

        maxWidth += this.getPaddingLeft() + this.getPaddingRight();
        maxHeight += this.getPaddingTop() + this.getPaddingBottom();
        maxHeight = Math.max(maxHeight, this.getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, this.getSuggestedMinimumWidth());
        Drawable drawable = this.getForeground();
        if (drawable != null) {
            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        this.setMeasuredDimension(View.resolveSizeAndState(maxWidth, shadowMeasureWidthMatchParent ? widthMeasureSpec : widthSpec, childState), View.resolveSizeAndState(maxHeight, shadowMeasureHeightMatchParent ? heightMeasureSpec : heightSpec, childState << 16));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutChildren();
    }

    private void layoutChildren() {
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() != View.GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp == null) {
                    break;
                }

                int childLeft = (int) (getPaddingLeft() + lp.leftMargin + shadowElevationLeft);
                int childTop = (int) (getPaddingTop() + lp.topMargin + shadowElevationTop);

                int groupWidth = (int) (mViewWidth - shadowElevationLeft - shadowElevationRight);
                int groupHeight = (int) (mViewHeight - shadowElevationTop - shadowElevationBottom);

                int childRight = childLeft + (child.getMeasuredWidth() > groupWidth ? groupWidth : child.getMeasuredWidth());
                int childBottom = childTop + (child.getMeasuredHeight() > groupHeight ? groupHeight : child.getMeasuredHeight());
                child.layout(childLeft, childTop, childRight, childBottom);
            }
        }
    }


    @Override
    public void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs) {
        SIZE_UNSET = -1;

        selfBounds = new Rect();
        overlayBounds = new Rect();
        paint = new Paint();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BetterCardView3);

        Drawable drawable = typedArray.getDrawable(R.styleable.BetterCardView3_android_foreground);
        if (drawable != null) {
            setForeground(drawable);
        }

        float cardElevation = typedArray.getDimensionPixelSize(R.styleable.BetterCardView3_cardElevation, SIZE_UNSET);
        if (cardElevation >= 0) {
            shadowElevationTop = cardElevation * 0.25f;
            shadowElevationLeft = cardElevation * 0.5f;
            shadowElevationRight = cardElevation * 0.5f;
            shadowElevationBottom = cardElevation;
        }
        typedArray.recycle();

        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        setBackground(null);
    }

    protected void onDraw(@Nullable Canvas canvas) {
        drawShadow(canvas);
        super.onDraw(canvas);
    }

    private void drawShadow(Canvas canvas) {

    }


    //限制子布局视图
    @Override
    protected void dispatchDraw(Canvas canvas) {
        Path path = getRoundedPath();
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }

    //按下的水波样式
    public void draw(@Nullable Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.save();
            Path path = getRoundedPath();
            canvas.clipPath(path);
            drawForeground(canvas);
            canvas.restore();
        }
    }

    public final void drawForeground(@Nullable Canvas canvas) {
        Drawable var10000 = foregroundDraw;
        if (foregroundDraw != null) {
            Drawable var2 = var10000;
            int w = getRight() - getLeft();
            int h = getBottom() - getTop();
            selfBounds.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
            Gravity.apply(119, var2.getIntrinsicWidth(), var2.getIntrinsicHeight(), selfBounds, overlayBounds);
            var2.setBounds(overlayBounds);
            var2.draw(canvas);
        }

    }

    public Drawable getForeground() {
        return foregroundDraw;
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == foregroundDraw;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable var10000 = foregroundDraw;
        if (foregroundDraw != null) {
            Drawable var1 = var10000;
            var1.jumpToCurrentState();
        }

    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable var10000 = foregroundDraw;
        if (foregroundDraw != null) {
            Drawable var1 = var10000;
            var10000 = var1.isStateful() ? var1 : null;
            if (var10000 != null) {
                var1 = var10000;
                var1.setState(getDrawableState());
            }
        }

    }

    public void setForeground(@Nullable Drawable drawable) {
        if (foregroundDraw != null) {
            Drawable var10000 = foregroundDraw;
            if (foregroundDraw != null) {
                var10000.setCallback((Drawable.Callback) null);
            }

            unscheduleDrawable(foregroundDraw);
        }

        foregroundDraw = drawable;
        updateForegroundColor();
        if (drawable != null) {
            setWillNotDraw(false);
            drawable.setCallback((Drawable.Callback) this);
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }

            Rect padding = new Rect();
            drawable.getPadding(padding);
        }

        requestLayout();
        invalidate();
    }

    private final void updateForegroundColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            RippleDrawable var10000 = (RippleDrawable) foregroundDraw;
            if ((RippleDrawable) foregroundDraw != null) {
                var10000.setColor(ColorStateList.valueOf(foregroundColor));
            }
        } else {
            Drawable var1 = foregroundDraw;
            if (foregroundDraw != null) {
                var1.setColorFilter(foregroundColor, PorterDuff.Mode.SRC_ATOP);
            }
        }

    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (Build.VERSION.SDK_INT >= 21) {
            Drawable var10000 = foregroundDraw;
            if (foregroundDraw != null) {
                Drawable var3 = var10000;
                var3.setHotspot(x, y);
            }
        }

    }

    private Path getRoundedPath() {

        float left = shadowElevationLeft;
        float top = shadowElevationTop;
        float right = getMeasuredWidth() - shadowElevationRight;
        float bottom = getMeasuredHeight() - shadowElevationBottom;
        float tl = cornerRadiusTL;
        float tr = cornerRadiusTR;
        float br = cornerRadiusBR;
        float bl = cornerRadiusBL;

        Path path = new Path();
        Float width = right - left;
        Float height = bottom - top;
        if (tl > Math.min(width, height) / 2) tl = Math.min(width, height) / 2;
        if (tr > Math.min(width, height) / 2) tr = Math.min(width, height) / 2;
        if (br > Math.min(width, height) / 2) br = Math.min(width, height) / 2;
        if (bl > Math.min(width, height) / 2) bl = Math.min(width, height) / 2;

        path.moveTo(right, top + tr);
        if (tr > 0)
            path.rQuadTo(0f, -tr, -tr, -tr);
        else {
            path.rLineTo(0f, -tr);
            path.rLineTo(-tr, 0f);
        }
        path.rLineTo(-(width - tr - tl), 0f);
        if (tl > 0)
            path.rQuadTo(-tl, 0f, -tl, tl);
        else {
            path.rLineTo(-tl, 0f);
            path.rLineTo(0f, tl);
        }
        path.rLineTo(0f, height - tl - bl);

        if (bl > 0)
            path.rQuadTo(0f, bl, bl, bl);
        else {
            path.rLineTo(0f, bl);
            path.rLineTo(bl, 0f);
        }

        path.rLineTo(width - bl - br, 0f);
        if (br > 0)
            path.rQuadTo(br, 0f, br, -br);
        else {
            path.rLineTo(br, 0f);
            path.rLineTo(0f, -br);
        }

        path.rLineTo(0f, -(height - br - tr));

        path.close();

        return path;
    }
}