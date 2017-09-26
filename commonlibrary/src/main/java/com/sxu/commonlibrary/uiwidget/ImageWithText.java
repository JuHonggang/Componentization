package com.sxu.commonlibrary.uiwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sxu.commonlibrary.R;

/*******************************************************************************
 * FileName: ImageWithText
 * <p/>
 * Description: 在图片上添加文字（用于地图标注）
 * <p/>
 * Author: juhg
 * <p/>
 * Version: v1.0
 * <p/>
 * Date: 16/4/15
 * <p/>
 * Copyright: all rights reserved by zhinanmao.
 *******************************************************************************/
public class ImageWithText extends ImageView {

    private String text;
    private int textSize;
    private int textColor;
    // 解决不规则图片中文字显示的位置问题
    private int offsetX = 0;
    private int offsetY = 0;

    private final int DEFAULT_TEXT_SIZE = 42;

    public ImageWithText(Context context) {
        super(context);
    }

    public ImageWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageWithText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(text)) {
            super.onDraw(canvas);
        } else {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                Bitmap bitmap = drawableToBitmap(drawable);
                BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(bitmapShader);
                canvas.drawBitmap(bitmap, getMatrix(), paint);
                if (!TextUtils.isEmpty(text)) {
                    Paint textPaint = new Paint();
                    textColor = textColor != 0 ? textColor : getResources().getColor(R.color.cl_b1);
                    textSize = textSize != 0 ? textSize : DEFAULT_TEXT_SIZE;
                    textPaint.setColor(textColor);
                    textPaint.setTextSize(textSize);
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    textPaint.setAntiAlias(true);
                    int left = (int)(getWidth() / 2.0f);
                    int bottom = (int)((Math.abs(getHeight() + textSize)) / 2.0f);
                    canvas.drawText(text, left + offsetX, bottom + offsetY, textPaint);
                }
            }
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        int width = getWidth();
        int height = getHeight();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.draw(canvas);
        }
        // 对图片进行缩放
        if (width < drawableWidth || height < drawableHeight) {
            bitmap = zoomImage(bitmap, width, height);
        }

        return bitmap;
    }

    private Bitmap zoomImage(Bitmap sourceBitmap, float newWidth, float newHeight) {
        float width = sourceBitmap.getWidth();
        float height = sourceBitmap.getHeight();
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, (int) width, (int) height, matrix, true);

        return bitmap;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
