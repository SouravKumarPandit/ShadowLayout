package com.pandit.sourav.shadowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ShadowRectLayout extends LinearLayout {


    public final Context mContext;
    private Paint shadowPaint;
    private RectF rectF;
    int shadowRadius = 10;
    float offSetX = -3;
    float offSetY = 3;
    int baseBackgroundColor = 0xffffffff;
    private int roundCornerRadius = 0;
    private int shadowColor = 0xffdedede;
    private int mMaxChildren = 1;
    //    private int layoutLeftPadding = 0;
//    private int layoutRightPadding = 0;
//    private int layoutTopPadding = 0;
//    private int layoutBottomPadding = 0;
    private int shadowLeft = 1;
    private int shadowRight = 1;
    private int shadowBottom = 1;
    private int shadowTop = 1;
    private boolean bShadowLeft = true;
    private boolean bShadowRight = true;
    private boolean bShadowBottom = true;


    private boolean bShadowTop = true;

    public ShadowRectLayout(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        initView(mContext, null);

    }

    public ShadowRectLayout(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        initView(mContext, attrs);

    }


//    public RoundLinearLayout getRoundLinearLayout() {
//        return roundLinearLayout;
//    }
//    private RoundLinearLayout roundLinearLayout;

    private void initView(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        shadowPaint = new Paint();
        rectF = new RectF();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShadowRectLayout);
            roundCornerRadius = a.getDimensionPixelSize(R.styleable.ShadowRectLayout_roundCornerRadius, roundCornerRadius);
            shadowColor = a.getColor(R.styleable.ShadowRectLayout_shadowRectColor, Color.LTGRAY);
            offSetX = a.getFloat(R.styleable.ShadowRectLayout_offsetX, -3);
            offSetY = a.getFloat(R.styleable.ShadowRectLayout_offsetY, 3);
            shadowRadius = a.getInt(R.styleable.ShadowRectLayout_shadowRadius, 10);
            baseBackgroundColor = a.getColor(R.styleable.ShadowRectLayout_baseColor, baseBackgroundColor);
//            layoutLeftPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayout_layout_Left_Padding, 0);
//            layoutRightPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayout_layout_Right_Padding, 0);
//            layoutTopPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayout_layout_Top_Padding, 0);
//            layoutBottomPadding = a.getDimensionPixelOffset(R.styleable.ShadowRectLayout_layout_Bottom_padding, 0);
            bShadowLeft = a.getBoolean(R.styleable.ShadowRectLayout_shadow_left, true);
            bShadowRight = a.getBoolean(R.styleable.ShadowRectLayout_shadow_Right, true);
            bShadowBottom = a.getBoolean(R.styleable.ShadowRectLayout_shadow_bottom, true);
            bShadowTop = a.getBoolean(R.styleable.ShadowRectLayout_shadow_Top, true);
            getRenderShadow();

            a.recycle();
//            if (false/*todo change it to autoColor true*/)
//            shadowColor=getDarkerColor(shadowColor);
        }
        intitView(context);

    }

    private void intitView(Context context) {
        shadowPaint.setColor(baseBackgroundColor);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row, col, left, top;
        View view = getChildAt(0);
        if (view != null) {
            int radii = (int) (shadowRadius*1.8);

            view.layout(radii*shadowLeft+roundCornerRadius/2, radii*shadowTop+roundCornerRadius/2, getWidth() - radii*shadowRight-roundCornerRadius/2, getHeight() - radii*shadowBottom-roundCornerRadius/2);
        }


    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        shadowPaint.setShadowLayer(shadowRadius, offSetX, offSetY, this.shadowColor);
        float rectValue = (float) (shadowRadius*1.8);
        rectF.set(rectValue * shadowLeft, rectValue * shadowTop, canvas.getWidth() - rectValue * shadowRight, canvas.getHeight() - rectValue * shadowBottom);
        canvas.drawRoundRect(rectF, roundCornerRadius, roundCornerRadius, shadowPaint);


        Drawable d = getGradientDrawable(mContext,R.drawable.metting_img,dpToPixel(50),0xBC8438C9,0xCBBE235E);
        d.setBounds((int)rectF.left,(int)rectF.top,(int)rectF.right,(int)rectF.bottom);
        d.draw(canvas);
        canvas.save();



        super.dispatchDraw(canvas);
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > mMaxChildren) {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > mMaxChildren) {
            throw new IllegalStateException(" cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > mMaxChildren) {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > mMaxChildren) {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (getChildCount() > mMaxChildren) {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }
        super.addView(child, width, height);
    }

    public void getRenderShadow() {
        if (!bShadowLeft) shadowLeft = 0;
        if (!bShadowRight) shadowRight = 0;
        if (!bShadowBottom) shadowBottom = 0;
        if (!bShadowTop) shadowTop = 0;
    }


    public int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }

    public static int dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }

    public  Drawable getGradientDrawable(Context mContext, int imgDrawable, float fRadius, int color1, int color2 ) {
        float radius = fRadius;
        float[] m_arrfTopHalfOuterRadii =
                new float[]{fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius};
        Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(),imgDrawable);
        final float roundPx = (float) bitmap.getWidth() * radius;

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(radius);

        Drawable imageDrawable=roundedBitmapDrawable;


        GradientDrawable roundGradiantDrawable = new GradientDrawable();
//        gradientDrawable.setOrientation(GradientDrawable.Orientation.BL_TR);
        roundGradiantDrawable.setShape(GradientDrawable.LINEAR_GRADIENT);
        roundGradiantDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius,radius,radius});
        roundGradiantDrawable.setColors(new int[]{color1, color2});


        Drawable[] drawarray = {imageDrawable, roundGradiantDrawable};
        LayerDrawable layerdrawable = new LayerDrawable(drawarray);

//        int _nHalfOfCellHeight = m_nCellHeight / 2;
        layerdrawable.setLayerInset(0, 0, 0, 0, 0); //top half
        layerdrawable.setLayerInset(1, 0, 0, 0, 0); //top half
//        layerdrawable.setLayerInset(2, 0, _nHalfOfCellHeight, 0, 0); //bottom half

        return layerdrawable;
    }

    public void setShadowColor(@ColorInt int color) {
        this.shadowColor = color;
        invalidate();
    }

    public void setRoundCornerRadius(int roundCornerRadius) {
        this.roundCornerRadius = dpToPixel(roundCornerRadius);
        invalidate();
    }


    public int getRadius() {
        return shadowRadius;
    }

    public void setShadowRadius(int shadowRadius) {

        this.shadowRadius = dpToPixel(shadowRadius);
        invalidate();

    }

    public float getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(int offSetX) {
        if (offSetX>shadowRadius)
        {
            this.offSetX = shadowRadius;
            invalidate();
            return;
        }
        this.offSetX = dpToPixel(offSetX);
        invalidate();

    }

    public void setOffSetY(int offSetY) {
        if (offSetY>shadowRadius)
        {
            this.offSetY = shadowRadius;
            invalidate();
            return;
        }
        this.offSetY = dpToPixel(offSetY);
        invalidate();

    }

    public int getBaseBackgroundColor() {
        return baseBackgroundColor;
    }

    public void setBaseBackgroundColor(int baseBackgroundColor) {
        this.baseBackgroundColor = baseBackgroundColor;
        shadowPaint.setColor(baseBackgroundColor);
        invalidate();

    }


    public void setShadowLeft(boolean bShadowLeft) {
        this.bShadowLeft = bShadowLeft;
        getRenderShadow();
        invalidate();
    }

    public void setShadowRight(boolean bShadowRight) {
        this.bShadowRight = bShadowRight;
        getRenderShadow();
        invalidate();
    }

    public void setShadowBottom(boolean bShadowBottom) {
        this.bShadowBottom = bShadowBottom;
        getRenderShadow();
        invalidate();
    }

    public void setShadowTop(boolean bShadowTop) {
        this.bShadowTop = bShadowTop;
        getRenderShadow();
        invalidate();
    }

    public boolean isbShadowRight() {
        return bShadowRight;
    }

    public boolean isbShadowLeft() {
        return bShadowLeft;
    }

    public boolean isbShadowBottom() {
        return bShadowBottom;

    }

    public boolean isbShadowTop() {
        return bShadowTop;
    }
}
