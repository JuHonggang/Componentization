package com.sxu.commonlibrary.commonutils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *
 * 启动其他APP
 *
 * @author Freeman
 *
 * @date 17/6/20
 */


public class LaunchUtil {

	/**
	 * 打开浏览器
	 * @param context
	 */
	public static void openBrowse(Context context) {
		context.startActivity(new Intent(Intent.ACTION_VIEW));
	}

	/**
	 * 打开应用市场
	 * @param context
	 */
	public void openAppMarket(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
		context.startActivity(intent);
	}

	/**
	 * 发送短信
	 * @param context
	 * @param telNumber
	 * @param content
	 */
	public static void sendSMS(Context context, String telNumber, String content) {
		if (VerificationUtil.isValidTelNumber(telNumber)) {
			Uri uri = Uri.parse("smsto:" + telNumber);
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.putExtra("sms_body", content);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	/**
	 * 打开短信界面
	 * @param context
	 * @param telNumber
	 */
	public static void openMessage(Context context, String telNumber) {
		if (VerificationUtil.isValidTelNumber(telNumber)) {
			Uri uri = Uri.parse("smsto:" + telNumber);
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	/**
	 * 打开拨号界面
	 * @param context
	 * @param telNumber
	 */
	public static void openPhone(Context context, String telNumber) {
		if (VerificationUtil.isValidTelNumber(telNumber)) {
			Uri uri = Uri.parse("tel:" + telNumber);
			Intent intent = new Intent(Intent.ACTION_CALL, uri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
}
