package com.sxu.commonlibrary.commonutils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * 自定义Toast的样式，位置及显示时间
 *
 * @author Freeman
 *
 * @date 17/6/20
 */


public class ToastUtil {

	public static void show(Context context, String info) {
		final Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		TextView textView = new TextView(context);
		textView.setText(info);
		textView.setTextSize(16);
		textView.setTextColor(Color.WHITE);
		textView.setGravity(Gravity.CENTER);
		textView.offsetLeftAndRight(DisplayUtil.dpToPx(60));
		int verticalPadding = DisplayUtil.dpToPx(13);
		int horizontalPadding = DisplayUtil.dpToPx(18);
		textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
		//textView.setBackgroundResource(R.drawable.toast_bg);
		toast.setView(textView);
		toast.setGravity(Gravity.CENTER, 0, DisplayUtil.dpToPx(60));
		toast.show();
		textView.postDelayed(new Runnable() {
			@Override
			public void run() {
				toast.cancel();
			}
		}, 1200);
	}
}
