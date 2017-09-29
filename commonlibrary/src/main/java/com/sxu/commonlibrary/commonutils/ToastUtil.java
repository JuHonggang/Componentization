package com.sxu.commonlibrary.commonutils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.sxu.commonlibrary.R;

/**
 *
 * 自定义Toast的样式，位置及显示时间
 *
 * @author Freeman
 *
 * @date 17/6/20
 */


public class ToastUtil {

	/**
	 * 注意：如果将activity theme中的fitsSystemWindows设置为true, toast会出现文件和背景错位的情况，
	 *  且自定义样式时设置setPadding无效
	 * @param context
	 * @param info
	 */
	public static void show(Context context, String info) {
		final Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		TextView textView = new TextView(context);
		textView.setText(info);
		textView.setTextSize(16);
		textView.setTextColor(Color.WHITE);
		textView.setGravity(Gravity.CENTER);
		int verticalPadding = DisplayUtil.dpToPx(8);
		int horizontalPadding = DisplayUtil.dpToPx(10);
		textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
		ViewBgUtil.setShapeBg(textView, GradientDrawable.RECTANGLE,
				ResourceUtil.getColor(context, R.color.cl_b1), DisplayUtil.dpToPx(6));
		toast.setView(textView);
		toast.setGravity(Gravity.CENTER, 0, DisplayUtil.dpToPx(70));
		toast.show();
		textView.postDelayed(new Runnable() {
			@Override
			public void run() {
				toast.cancel();
			}
		}, 1200);
	}
}
