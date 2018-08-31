/*
 *
 * # Copyright 2018 Sourav Kaumar Pandit
 * #
 * # Licensed under the Apache License, Version 2.0 (the "License");
 * # you may not use this file except in compliance with the License.
 * # You may obtain a copy of the License at
 * #
 * #     http://www.apache.org/licenses/LICENSE-2.0
 * #
 * # Unless required by applicable law or agreed to in writing, software
 * # distributed under the License is distributed on an "AS IS" BASIS,
 * # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * # See the License for the specific language governing permissions and
 * # limitations under the License.
 */

package com.pandit.sourav.shadowlayout;

import android.content.Context;
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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/*
# Copyright 2018 Sourav Kaumar pandit
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.*/

public class ShadowRectLayout extends ViewGroup {
    public final Context mContext;
    private Paint shadowPaint;
    private RectF rectF;
    int shadowRadius = 10;
    float offSetX = -3;
    float offSetY = 3;
    int baseBackgroundColor = 0xffffffff;
    private int roundCornerRadius = 0;
    private int shadowColor = 0xFFCECECE;
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


    static final float SHADOW_MULTIPLIER = 1.8f;
    private float[] arrFlotCornerRadii;

    boolean shadowColorAuto;

    public ShadowRectLayout(Context mContext)
    {
        super(mContext);
        this.mContext = mContext;
        initView(mContext, null);

    }

    public ShadowRectLayout(Context mContext, @Nullable AttributeSet attrs)
    {
        super(mContext, attrs);
        this.mContext = mContext;
        initView(mContext, attrs);

    }

    private void initView(Context context, AttributeSet attrs)
    {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        shadowPaint = new Paint();
        rectF = new RectF();
        if (attrs != null)
        {
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
        }
        initView(context);


    }

    private void initView(Context context)
    {
        shadowPaint.setColor(baseBackgroundColor);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int radii = (int) (shadowRadius * SHADOW_MULTIPLIER);
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        // from their size.
        final View child = getChildAt(0);
        if (child == null || child.getVisibility() == GONE)
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        // Measure the child.
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
        maxHeight += Math.max(maxHeight, child.getMeasuredHeight());
        childState = combineMeasuredStates(childState, child.getMeasuredState());

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());


        int leftSide = radii * shadowLeft;
        int topSide = radii * shadowTop;
        int rightSide = radii * shadowRight;
        int bottomSide = radii * shadowBottom;
        maxHeight = maxHeight + topSide + rightSide;
        maxWidth = maxWidth + leftSide + bottomSide;


        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState), resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        View view = getChildAt(0);
        if (view!=null){
            int radii = (int) (shadowRadius * SHADOW_MULTIPLIER);
            int left = radii * shadowLeft;
            int top = radii * shadowLeft;
            int right = radii * shadowRight;
            int bottom = radii * shadowLeft;
            view.layout(left, top, getWidth() - right, getHeight() - bottom);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        shadowPaint.setShadowLayer(shadowRadius, offSetX, offSetY, this.shadowColor);
        float rectValue = (float) (shadowRadius * SHADOW_MULTIPLIER);
        rectF.set(rectValue * shadowLeft, rectValue * shadowTop, canvas.getWidth() - rectValue * shadowRight, canvas.getHeight() - rectValue * shadowBottom);
        canvas.drawRoundRect(rectF, roundCornerRadius, roundCornerRadius, shadowPaint);

        Drawable backgroundDrawable = getGradientDrawable(mContext, resDrawable, roundCornerRadius, imgGradientColor1, imgGradientColor2);
        if (backgroundDrawable != null)
        {
            backgroundDrawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            backgroundDrawable.draw(canvas);
        }
        canvas.save();
        super.dispatchDraw(canvas);
    }

    @Override
    public void addView(View child)
    {
        if (getChildCount() > mMaxChildren)
        {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }
        super.addView(child);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
    }

    @Override
    public void addView(View child, int index)
    {
        if (getChildCount() > mMaxChildren)
        {
            throw new IllegalStateException(" cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, LayoutParams params)
    {
        if (getChildCount() > mMaxChildren)
        {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, LayoutParams params)
    {
        if (getChildCount() > mMaxChildren)
        {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height)
    {
        if (getChildCount() > mMaxChildren)
        {
            throw new IllegalStateException("cannot have more than " + mMaxChildren + " direct children");
        }
        super.addView(child, width, height);
    }

    public void getRenderShadow()
    {
        if (!bShadowLeft)
            shadowLeft = 0;
        if (!bShadowRight)
            shadowRight = 0;
        if (!bShadowBottom)
            shadowBottom = 0;
        if (!bShadowTop)
            shadowTop = 0;
    }


    public int getDarkerColor(int color)
    {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }


    public Drawable getGradientDrawable(Context mContext, int imgDrawable, float fRadius, int color1, int color2)
    {
        arrFlotCornerRadii = new float[]{fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius};

        if (imgDrawable > 0)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), imgDrawable);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, getWidth(), getHeight());

            if (roundedBitmapDrawable == null)
                roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);

            roundedBitmapDrawable.setCornerRadius(fRadius);

        }

        if (color1 != -1 || color2 != -1)
        {

            if (roundGradiantDrawable == null)
                roundGradiantDrawable = new GradientDrawable();
            roundGradiantDrawable.setShape(GradientDrawable.LINEAR_GRADIENT);
            roundGradiantDrawable.setCornerRadii(new float[]{fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius, fRadius});
            if (color1 != -1 && color2 == -1)
                roundGradiantDrawable.setColor(color1);
            else if (color1 == -1 && color2 != -1)
                roundGradiantDrawable.setColor(color2);
            else
                roundGradiantDrawable.setColors(new int[]{color1, color2});
        }

        if (roundedBitmapDrawable != null && roundGradiantDrawable != null)
        {
            Drawable[] drawableArray = {roundedBitmapDrawable, roundGradiantDrawable};
            layerdrawable = new LayerDrawable(drawableArray);
            layerdrawable.setLayerInset(0, 0, 0, 0, 0);
            layerdrawable.setLayerInset(1, 0, 0, 0, 0);

        } else if (roundedBitmapDrawable == null && roundGradiantDrawable != null)
        {
            Drawable[] drawarray = {roundGradiantDrawable};
            layerdrawable = new LayerDrawable(drawarray);
            layerdrawable.setLayerInset(0, 0, 0, 0, 0);
        } else if (roundedBitmapDrawable != null && roundGradiantDrawable == null)
        {
            Drawable[] drawarray = {roundedBitmapDrawable};
            layerdrawable = new LayerDrawable(drawarray);
            layerdrawable.setLayerInset(0, 0, 0, 0, 0);
        } else
            return null;


        return layerdrawable;
    }


    public void setShadowColor(@ColorInt int color)
    {
        this.shadowColorAuto=false;
        this.shadowColor = color;
        invalidate();
    }

    public void setRoundCornerRadius(int roundCornerRadius)
    {
        this.roundCornerRadius = roundCornerRadius;
        invalidate();
    }

    private double scaleRange(final double valueIn, final double baseMin, final double baseMax, final double limitMin, final double limitMax)
    {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }


    public int getRadius()
    {
        return shadowRadius;
    }

    public void setShadowRadius(int shadowRadius)
    {

        this.shadowRadius = shadowRadius;
        invalidate();

    }

    public float getOffSetX()
    {
        return offSetX;
    }

    public void setOffSetX(int offSetX)
    {
        if (offSetX > shadowRadius)
        {
            this.offSetX = shadowRadius;
            invalidate();
            return;
        }
        this.offSetX = offSetX;
        invalidate();

    }

    public void setOffSetY(int offSetY)
    {
        if (offSetY > shadowRadius)
        {
            this.offSetY = shadowRadius;
            invalidate();
            return;
        }
        this.offSetY = offSetY;
        invalidate();

    }

    public int getBaseBackgroundColor()
    {
        return baseBackgroundColor;
    }

    public void setBaseBackgroundColor(int baseBackgroundColor)
    {
        this.baseBackgroundColor = baseBackgroundColor;
        shadowPaint.setColor(baseBackgroundColor);
        invalidate();

    }

    public boolean isShadowColorAuto()
    {
        return shadowColorAuto;
    }

    public void setShadowColorAuto(boolean shadowColorAuto)
    {
        if (imgGradientColor1 != -1)
            setShadowColor(imgGradientColor1);
        else if (imgGradientColor2 != -1)
            setShadowColor(imgGradientColor2);
        else
            setShadowColor(Color.GRAY);
        this.shadowColorAuto = shadowColorAuto;


    }


    public int getImgGradientColor1()
    {
        return imgGradientColor1;

    }

    public void setImgGradientColor1(int imgGradientColor1)
    {

        this.imgGradientColor1 = imgGradientColor1;
        if (shadowColorAuto && imgGradientColor1 != -1)
            this.shadowColor = imgGradientColor1;
        invalidate();

    }

    public int getImgGradientColor2()
    {
        return imgGradientColor2;

    }

    public void setImgGradientColor2(int imgGradientColor2)
    {
        this.imgGradientColor2 = imgGradientColor2;
        if (shadowColorAuto && imgGradientColor2 != -1)
            this.shadowColor = imgGradientColor2;
        invalidate();
    }


    public int getResDrawable()
    {
        return resDrawable;
    }

    public void setResDrawable(@DrawableRes int resDrawable)
    {
        this.resDrawable = resDrawable;
        invalidate();

    }

    public void setShadowLeft(boolean bShadowLeft)
    {
        this.bShadowLeft = bShadowLeft;
        getRenderShadow();
        invalidate();
    }

    public void setShadowRight(boolean bShadowRight)
    {
        this.bShadowRight = bShadowRight;
        getRenderShadow();
        invalidate();
    }

    public void setShadowBottom(boolean bShadowBottom)
    {
        this.bShadowBottom = bShadowBottom;
        getRenderShadow();
        invalidate();
    }

    public void setShadowTop(boolean bShadowTop)
    {
        this.bShadowTop = bShadowTop;
        getRenderShadow();
        invalidate();
    }

    public boolean isShadowRight()
    {
        return bShadowRight;
    }

    public boolean isShadowLeft()
    {
        return bShadowLeft;
    }

    public boolean isShadowBottom()
    {
        return bShadowBottom;

    }

    public boolean isShadowTop()
    {
        return bShadowTop;
    }
}
