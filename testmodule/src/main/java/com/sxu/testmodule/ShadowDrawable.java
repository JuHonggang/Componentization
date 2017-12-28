package com.sxu.testmodule;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import static android.R.attr.direction;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/10/25
 */


public class ShadowDrawable extends ShapeDrawable {

	private Paint mPaint;
	private int mColor;
	private float mRadius;

	public ShadowDrawable() {
		this(10, Color.BLACK);
	}

	public ShadowDrawable(int color, int radius) {
		this.mColor = color;
		this.mRadius = radius;
		init();
	}

	@Override
	public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
		super.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(@ColorInt int color, @NonNull PorterDuff.Mode mode) {
		super.setColorFilter(color, mode);
	}

	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {
		super.setColorFilter(colorFilter);
	}

	@Override
	public int getOpacity() {
		return super.getOpacity();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAlpha(0);
		//设置画笔的 style
		mPaint.setStyle(Paint.Style.FILL);
		//设置画笔的模糊效果
		//mPaint.setMaskFilter(new BlurMaskFilter(1000, BlurMaskFilter.Blur.INNER));
		//mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{ 1, 1, 1 }, 0.3f, 6, 10f));
		//设置画笔的颜色
		mPaint.setColor(mColor);
	}

	@Override
	public void draw(@NonNull Canvas canvas) {
//		Paint paint3 = new Paint();
//		paint3.setColor(Color.RED);
//		paint3.setShadowLayer(50, 5, 2, Color.GREEN);
//		canvas.drawCircle(300, 72,72, paint3);
		mPaint.setShadowLayer(100, 100, 100, mColor);
		canvas.drawRoundRect(new RectF(0, 0, 600, 144), mRadius, mRadius, mPaint);
		super.draw(canvas);
//		//canvas.translate(-10, -10);
//		RectF newRect = new RectF(0, 0, 600, 144);
//		Paint otherPaint = new Paint();
//		otherPaint.setColor(Color.WHITE);
//		//绘制阴影效果
//		canvas.drawRoundRect(newRect, mRadius, mRadius, otherPaint);
	}
}
