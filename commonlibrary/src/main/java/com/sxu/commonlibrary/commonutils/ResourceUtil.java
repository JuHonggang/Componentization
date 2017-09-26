package com.sxu.commonlibrary.commonutils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.sxu.commonlibrary.R;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/21
 */


public class ResourceUtil {

	public static int getColor(Context context, int colorId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return context.getResources().getColor(colorId, null);
		} else {
			return context.getResources().getColor(colorId);
		}
	}

	public static Drawable getDrawable(Context context, int colorId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return context.getResources().getDrawable(colorId, null);
		} else {
			return context.getResources().getDrawable(colorId);
		}
	}
}
