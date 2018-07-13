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
    private int shadowLeft = 1;
    private int shadowRight = 1;
    private int shadowBottom = 1;
    private int shadowTop = 1;
    private int imgGradientColor1 = -1;
    private int imgGradientColor2 = -1;

    private boolean bShadowLeft = true;
    private boolean bShadowRight = true;
    private boolean bShadowBottom = true;

    private boolean bShadowTop = true;
    private int resDrawable = -1;

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
            resDrawable = a.getInt(R.styleable.ShadowRectLayout_imgBackground, -1);
            imgGradientColor1 = a.getColor(R.styleable.ShadowRectLayout_imgGradientColor1, -1);
            imgGradientColor2 = a.getColor(R.styleable.ShadowRectLayout_imgGradientColor2, -1);
            baseBackgroundColor = a.getColor(R.styleable.ShadowRectLayout_baseColor, baseBackgroundColor);
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
/*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        View childView = getChildAt(0);
        if (childView != null) {

            // Measure icon.
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);

            // Figure out how much width the icon used.
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int widthUsed = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;


            // Figure out how much total space the icon used.
            int iconWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int iconHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;


            // The width taken by the children + padding.
            int width = getPaddingTop() + getPaddingBottom() +
                    iconWidth + Math.max(0, iconWidth);
            // The height taken by the children + padding.
            int height = getPaddingTop() + getPaddingBottom() +
                    Math.max(0, iconHeight);

            // Reconcile the measured dimensions with the this view's constraints and
            // set the final measured width and height.
            setMeasuredDimension(
                    resolveSize(width, widthMeasureSpec),
                    resolveSize(height, heightMeasureSpec)
            );
        } else super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

    }*/


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        int radii = (int) (shadowRadius * 1.8);
        int left;//= radii * shadowLeft + roundCornerRadius / 2;
        int top;//= radii * shadowTop + roundCornerRadius / 2;
        int right;//= getWidth() - radii * shadowRight - roundCornerRadius / 2;
        int bottom;//= getHeight() - radii * shadowBottom - roundCornerRadius / 2;
        renderRoundCornerRadius(roundCornerRadius);

      /*  if (getWidth() / 2 == getHeight() / 2 && (getWidth() / 2 <= roundCornerRadius || getHeight() / 2 <= roundCornerRadius)) {

            roundCornerRadius = (int) scale(roundCornerRadius, getWidth(), 0, 0, getWidth() / 2);

            left = getWidth() / 6;
            top = getHeight() / 6;
            right = getWidth() - getWidth() / 6;
            bottom = getHeight() - getHeight() / 6;

        } else if (getWidth() / 2 <= roundCornerRadius && getHeight() / 2 <= roundCornerRadius) {
            if (getWidth() / 2 > getHeight() / 2) {
                roundCornerRadius = (int) scale(roundCornerRadius, 0, getWidth()/2, 0, getWidth() / 2);
                left = radii * shadowLeft + roundCornerRadius / 2;
                top = radii * shadowTop + roundCornerRadius / 2;
                right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
                bottom = getHeight() - radii * shadowBottom - roundCornerRadius / 2;
            } else {
                roundCornerRadius = (int) scale(roundCornerRadius, 0, getHeight() / 2, 0, getHeight() / 2);
                left = radii * shadowLeft + roundCornerRadius / 2;
                top = radii * shadowTop + roundCornerRadius / 2;
                right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
                bottom = getHeight() - radii * shadowBottom - roundCornerRadius / 2;
            }
        } else if (getHeight() / 2 <= roundCornerRadius) {
            roundCornerRadius = (int) scale(roundCornerRadius, 0, getHeight() / 2, 0, getHeight() / 2);
            left = radii * shadowLeft + getWidth() / 6;
            top = radii * shadowTop;
            right = getWidth() - getWidth() / 6;
            bottom = getHeight() - radii * shadowBottom;
        } else if (getWidth() / 2 <= roundCornerRadius) {
            roundCornerRadius = (int) scale(roundCornerRadius, 0, getWidth() / 2, 0, getWidth() / 2);
            left = radii * shadowLeft + roundCornerRadius / 2;
            top = getWidth() / 6;
            right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
            bottom = getHeight() - getHeight() / 6;
        } else {
            left = radii * shadowLeft + roundCornerRadius / 2;
            top = radii * shadowTop + roundCornerRadius / 2;
            right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
            bottom = getHeight() - radii * shadowBottom - roundCornerRadius / 2;
        }
*/


       /* if (getWidth() == getHeight() && (roundCornerRadius >= getWidth() / 2 || roundCornerRadius <= getHeight() / 2)) {
            roundCornerRadius = getWidth() / 2;
            left = getWidth() / 6;
            top = radii * shadowTop + getWidth() / 6;*//*+ roundCornerRadius / 2*//*
            ;
            right = getWidth() - getWidth() / 6;
            bottom = getHeight() - radii * shadowBottom - getWidth() / 6 *//*- roundCornerRadius / 2*//*;


        }*//* else if (roundCornerRadius >= getHeight() / 2 && roundCornerRadius <= getWidth() / 2) {
            roundCornerRadius = getWidth() / 2;

            left = radii * shadowLeft + roundCornerRadius / 2;
            top = getWidth() / 6;
            right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
            bottom = getWidth() / 6;
        } *//* else {*/
//        left = radii * shadowLeft + roundCornerRadius / 2;
//        top = radii * shadowTop + roundCornerRadius / 2;
//        right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
//        bottom = getHeight() - radii * shadowBottom - roundCornerRadius / 2;

//        }
        left = radii * shadowLeft + roundCornerRadius / 2;
        top = radii * shadowTop + roundCornerRadius / 2;
        right = getWidth() - radii * shadowRight - roundCornerRadius / 2;
        bottom = getHeight() - radii * shadowBottom - roundCornerRadius / 2;
        if (view != null) {
            view.layout(left, top, right, bottom);
        }


    }

    private void renderRoundCornerRadius(int roundCornerRadius) {
        if (roundCornerRadius > 1000)
            roundCornerRadius = 1000;
        else if (roundCornerRadius < 0)
            roundCornerRadius = 0;
        if (getWidth() == getHeight())
            roundCornerRadius = (int) scale(roundCornerRadius, 0, 1000, 0, getWidth() / 2);
        else if (getWidth() < getHeight())
            roundCornerRadius = (int) scale(roundCornerRadius, 0, 1000, 0, getWidth() / 2);
        else
            roundCornerRadius = (int) scale(roundCornerRadius, 0, 1000, 0, getHeight() / 2);
        this.roundCornerRadius = roundCornerRadius;

    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        shadowPaint.setShadowLayer(shadowRadius, offSetX, offSetY, this.shadowColor);
        float rectValue = (float) (shadowRadius * 1.8);
        rectF.set(rectValue * shadowLeft, rectValue * shadowTop, canvas.getWidth() - rectValue * shadowRight, canvas.getHeight() - rectValue * shadowBottom);
        canvas.drawRoundRect(rectF, roundCornerRadius, roundCornerRadius, shadowPaint);

        Drawable d = getGradientDrawable(mContext, resDrawable, roundCornerRadius, imgGradientColor1, imgGradientColor2);
        if (d != null) {
            d.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            d.draw(canvas);
        }
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

 /*   public static int dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }*/

    public Drawable getGradientDrawable(Context mContext, int imgDrawable, float fRadius, int color1, int color2) {
        //        float[] m_arrfTopHalfOuterRadii =new float[]{fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius};
        RoundedBitmapDrawable roundedBitmapDrawable = null;
        LayerDrawable layerdrawable = null;
        GradientDrawable roundGradiantDrawable = null;
        if (imgDrawable > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), imgDrawable);
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
            roundedBitmapDrawable.setCornerRadius(fRadius);
        }


        if (color1 != -1 || color2 != -1) {

            roundGradiantDrawable = new GradientDrawable();
            roundGradiantDrawable.setShape(GradientDrawable.LINEAR_GRADIENT);
            roundGradiantDrawable.setCornerRadii(new float[]{fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius});
            if (color1 != -1 && color2 == -1)
//                roundGradiantDrawable.setColors(new int[]{color1,0x00FFFFFF});
                roundGradiantDrawable.setColor(color1);
            else if (color1 == -1 && color2 != -1)
//                roundGradiantDrawable.setColors(new int[]{0x00FFFFFF,color2});
                roundGradiantDrawable.setColor(color2);
            else
                roundGradiantDrawable.setColors(new int[]{color1, color2});
        }

        if (roundedBitmapDrawable != null && roundGradiantDrawable != null) {
            Drawable[] drawableArray = {roundedBitmapDrawable, roundGradiantDrawable};
            layerdrawable = new LayerDrawable(drawableArray);
            layerdrawable.setLayerInset(0, 0, 0, 0, 0);
            layerdrawable.setLayerInset(1, 0, 0, 0, 0);

        } else if (roundedBitmapDrawable == null && roundGradiantDrawable != null) {
            Drawable[] drawarray = {roundGradiantDrawable};
            layerdrawable = new LayerDrawable(drawarray);
            layerdrawable.setLayerInset(0, 0, 0, 0, 0);
        } else if (roundedBitmapDrawable != null && roundGradiantDrawable == null) {
            Drawable[] drawarray = {roundedBitmapDrawable};
            layerdrawable = new LayerDrawable(drawarray);
            layerdrawable.setLayerInset(0, 0, 0, 0, 0);
        } else return null;


        return layerdrawable;
    }

    public void setShadowColor(@ColorInt int color) {
        this.shadowColor = color;
        invalidate();
    }

    public void setRoundCornerRadius(int roundCornerRadius) {
        this.roundCornerRadius = roundCornerRadius;
        invalidate();
    }

    public double scale(final double valueIn, final double baseMin, final double baseMax, final double limitMin, final double limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }


    public int getRadius() {
        return shadowRadius;
    }

    public void setShadowRadius(int shadowRadius) {

        this.shadowRadius = shadowRadius;
        invalidate();

    }

    public float getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(int offSetX) {
        if (offSetX > shadowRadius) {
            this.offSetX = shadowRadius;
            invalidate();
            return;
        }
        this.offSetX = offSetX;
        invalidate();

    }

    public void setOffSetY(int offSetY) {
        if (offSetY > shadowRadius) {
            this.offSetY = shadowRadius;
            invalidate();
            return;
        }
        this.offSetY = offSetY;
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

    public int getImgGradientColor1() {
        return imgGradientColor1;

    }

    public void setImgGradientColor1(int imgGradientColor1) {
        this.imgGradientColor1 = imgGradientColor1;
        invalidate();

    }

    public int getImgGradientColor2() {
        return imgGradientColor2;

    }

    public void setImgGradientColor2(int imgGradientColor2) {
        this.imgGradientColor2 = imgGradientColor2;
        invalidate();
    }


    public int getResDrawable() {
        return resDrawable;
    }

    public void setResDrawable(int resDrawable) {
        this.resDrawable = resDrawable;
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
