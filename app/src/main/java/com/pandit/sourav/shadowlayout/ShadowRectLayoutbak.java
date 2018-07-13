package com.pandit.sourav.shadowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class ShadowRectLayoutbak extends LinearLayout {

    private Paint shadowPaint;
    private RectF rectF;
    int radius = 10;
    float offSetX = 0;
    float offSetY = 0;
    int baseBackgroundColor = 0xffffffff;
    private int shadowRound = 0;
    private int shadowColor = 0xffdedede;
    private boolean mInvalidate;

    private int layoutLeftPadding = 0;
    private int layoutRightPadding = 0;
    private int layoutTopPadding = 0;
    private int layoutBottomPadding = 0;
    private int shadowLeft = 1;
    private int shadowRight = 1;
    private int shadowBottom = 1;
    private int shadowTop = 1;
    private boolean bShadowLeft = true;
    private boolean bShadowRight = true;
    private boolean bShadowBottom = true;


    private boolean bShadowTop = true;

    public ShadowRectLayoutbak(Context context) {
        super(context);
        initView(context, null);

    }

    public ShadowRectLayoutbak(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);

    }


    public RoundLinearLayout getRoundLinearLayout() {
        return roundLinearLayout;
    }


    private RoundLinearLayout roundLinearLayout;

    private void initView(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        shadowPaint = new Paint();
        rectF = new RectF();
        shadowPaint.setColor(baseBackgroundColor);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);


        if (attrs != null) {
           /* TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShadowRectLayoutbak);
            shadowRound = a.getDimensionPixelSize(R.styleable.ShadowRectLayoutbak_roundCornerRadius, shadowRound);
            shadowColor = a.getColor(R.styleable.ShadowRectLayoutbak_shadowRectColor, Color.LTGRAY);
            offSetX = a.getFloat(R.styleable.ShadowRectLayoutbak_offsetX, -3);
            offSetY = a.getFloat(R.styleable.ShadowRectLayoutbak_offsetY, 3);
            radius = a.getInt(R.styleable.ShadowRectLayoutbak_shadowRadius, 10);
            baseBackgroundColor = a.getColor(R.styleable.ShadowRectLayoutbak_baseColor, baseBackgroundColor);
            layoutLeftPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayoutbak_layout_Left_Padding, 0);
            layoutRightPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayoutbak_layout_Right_Padding, 0);
            layoutTopPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayoutbak_layout_Top_Padding, 0);
            layoutBottomPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayoutbak_layout_Bottom_padding, 0);
            bShadowLeft = a.getBoolean(R.styleable.ShadowRectLayout_shadow_left, true);
            bShadowRight = a.getBoolean(R.styleable.ShadowRectLayout_shadow_Right, true);
            bShadowBottom = a.getBoolean(R.styleable.ShadowRectLayout_shadow_bottom, true);
            bShadowTop = a.getBoolean(R.styleable.ShadowRectLayout_shadow_Top, true);

            if (!bShadowLeft) shadowLeft = 0;
            if (!bShadowRight) shadowRight = 0;
            if (!bShadowBottom) shadowBottom = 0;
            if (!bShadowTop) shadowTop = 0;*/


//            if (false/*todo change it to autoColor true*/)
//            shadowColor=getDarkerColor(shadowColor);
        }

        roundLinearLayout = new RoundLinearLayout(context);
        roundLinearLayout.setPadding(layoutLeftPadding, layoutTopPadding, layoutRightPadding, layoutBottomPadding);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int rectValue = (int) (radius * 1.4f);
        params.setMargins(rectValue, rectValue, rectValue, rectValue);
        roundLinearLayout.setLayoutParams(params);
        roundLinearLayout.setRound(radius);
        addView(roundLinearLayout);


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int N = getChildCount();
                for (int i = 0; i < N; i++) {
                    View view = getChildAt(i);
                    if (i != 0) {
                        removeView(view);
                        getChildCount();
                        continue;
                    }
                    N = getChildCount();
                    mInvalidate = true;
                }

//                ((RoundLinearLayout) getChildAt(0)).setRound(shadowRound);
                mInvalidate = true;
            }
        });
        mInvalidate = true;

    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mInvalidate) {
            mInvalidate = false;
            shadowPaint.setShadowLayer(radius, offSetX, offSetY, this.shadowColor);
            float rectValue = (radius * 1.4f);

            rectF.set(rectValue * shadowLeft, rectValue * shadowTop, canvas.getWidth() - rectValue * shadowRight, canvas.getHeight() - rectValue * shadowBottom);
            canvas.drawRoundRect(rectF, shadowRound, shadowRound, shadowPaint);
            canvas.save();
        }
        super.dispatchDraw(canvas);
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }


    public int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }

    public void setShadowColor(@ColorInt int color) {
        this.shadowColor = color;
        invalidate();
        mInvalidate = true;
    }

    public void setShadowRoundRadius(int radius) {
        if (roundLinearLayout != null) {
            roundLinearLayout.setRound(radius);
        }
        this.shadowRound = radius;
        invalidate();
        mInvalidate = true;
    }


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
        mInvalidate = true;

    }

    public float getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(int offSetX) {
        this.offSetX = offSetX;
        invalidate();
        mInvalidate = true;

    }

    public void setOffSetY(int offSetY) {
        this.offSetY = offSetY;
        invalidate();
        mInvalidate = true;

    }

    public int getBaseBackgroundColor() {
        return baseBackgroundColor;
    }

    public void setBaseBackgroundColor(int baseBackgroundColor) {
        this.baseBackgroundColor = baseBackgroundColor;
        shadowPaint.setColor(baseBackgroundColor);
        invalidate();
        mInvalidate = true;

    }


    public boolean isbShadowLeft() {
        return bShadowLeft;
    }

    public void setbShadowLeft(boolean bShadowLeft) {
        this.bShadowLeft = bShadowLeft;
    }

    public boolean isbShadowRight() {
        return bShadowRight;
    }

    public void setbShadowRight(boolean bShadowRight) {
        this.bShadowRight = bShadowRight;
    }

    public boolean isbShadowBottom() {
        return bShadowBottom;
    }

    public void setbShadowBottom(boolean bShadowBottom) {
        this.bShadowBottom = bShadowBottom;
    }

    public boolean isbShadowTop() {
        return bShadowTop;
    }

    public void setbShadowTop(boolean bShadowTop) {
        this.bShadowTop = bShadowTop;
    }
}
