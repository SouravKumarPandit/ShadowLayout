package com.pandit.sourav.shadowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    RoundedBitmapDrawable roundedBitmapDrawable = null;
    LayerDrawable layerdrawable = null;
    GradientDrawable roundGradiantDrawable = null;

    static final double COS_45 = Math.cos(Math.toRadians(45));

    static final float SHADOW_MULTIPLIER = 1.8f;
    private boolean mAddPaddingForCorners = true;
    private float[] arrFlotCornerRadii;

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


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        renderRoundCornerRadius(roundCornerRadius);
        View view = getChildAt(0);
        int radii = (int) (shadowRadius * SHADOW_MULTIPLIER);
        int left;
        int top;
        int right;
        int bottom;


        if (getHeight() == getWidth()) {
            left = getWidth() / 6;
            top = getWidth() / 6;
            right = getWidth() - getWidth() / 6;
            bottom = getHeight() - getWidth() / 6;
            view.layout(left, top, right, bottom);
        } else if (getHeight() > getWidth()) {
            float scaleBy = (float) getWidth() / (float) getHeight();
            left = radii * shadowLeft;
            top = radii * shadowLeft;
            right = getWidth() - radii * shadowRight;
            bottom = getHeight() - radii * shadowLeft;
            int hOffset = (int) Math.ceil(calculateHorizontalPadding(radii, roundCornerRadius,
                    mAddPaddingForCorners));
            this.setPadding(left, hOffset + top, left, hOffset + top);
            view.layout(left, top + hOffset, right, bottom - hOffset);

        } else /*if (getHeight() < getWidth()) */ {
            float scaleBy = (float) getHeight() / (float) getWidth();
            left = radii * shadowLeft;
            top = radii * shadowTop;
            right = getWidth() - radii * shadowRight;
            bottom = getHeight() - radii * shadowBottom;
            int vOffset = (int) Math.ceil(calculateVerticalPadding(radii, roundCornerRadius,
                    mAddPaddingForCorners));
            this.setPadding(left + vOffset, top, left + vOffset, top);
            view.layout(left + vOffset, top, right - vOffset, bottom);
        }
    }


    private void renderRoundCornerRadius(int roundCornerRadius) {
        if (roundCornerRadius > 100)
            roundCornerRadius = 100;
        else if (roundCornerRadius < 0)
            roundCornerRadius = 0;

        if (getWidth() == getHeight())
            roundCornerRadius = (int) scaleRange(roundCornerRadius, 0, 100, 0, getWidth() / 2);
        else if (getWidth() < getHeight())
            roundCornerRadius = (int) scaleRange(roundCornerRadius, 0, 100, 0, getWidth() / 2);
        else
            roundCornerRadius = (int) scaleRange(roundCornerRadius, 0, 100, 0, getHeight() / 2);
        this.roundCornerRadius = roundCornerRadius;

    }

    public boolean getPadding(Rect padding) {
        int vOffset = (int) Math.ceil(calculateVerticalPadding(25, roundCornerRadius,
                mAddPaddingForCorners));
        int hOffset = (int) Math.ceil(calculateHorizontalPadding(25, roundCornerRadius,
                mAddPaddingForCorners));
        padding.set(hOffset, vOffset, hOffset, vOffset);
        return true;
    }

    void setShadowSize(float shadowSize, float maxShadowSize) {
        if (shadowSize < 0 || maxShadowSize < 0) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        shadowSize = toEven(shadowSize);
        maxShadowSize = toEven(maxShadowSize);

    }

    private static int toEven(float value) {
        int i = Math.round(value);
        return (i % 2 == 1) ? i - 1 : i;
    }

    public float calculateVerticalPadding(float maxShadowSize, float cornerRadius,
                                          boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius);
        } else {
            return maxShadowSize * SHADOW_MULTIPLIER;
        }
    }

    public float calculateHorizontalPadding(float maxShadowSize, float cornerRadius,
                                            boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (maxShadowSize + (1 - COS_45) * cornerRadius);
        } else {
            return maxShadowSize;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        shadowPaint.setShadowLayer(shadowRadius, offSetX, offSetY, this.shadowColor);
        float rectValue = (float) (shadowRadius * SHADOW_MULTIPLIER);
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


    public Drawable getGradientDrawable(Context mContext, int imgDrawable, float fRadius, int color1, int color2) {
        arrFlotCornerRadii = new float[]{fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius};

        if (imgDrawable > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), imgDrawable);
            if (roundedBitmapDrawable == null)
                roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
            roundedBitmapDrawable.setCornerRadius(fRadius);

        }


        if (color1 != -1 || color2 != -1) {

            if (roundGradiantDrawable == null)
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

    public double scaleRange(final double valueIn, final double baseMin, final double baseMax, final double limitMin, final double limitMax) {
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
