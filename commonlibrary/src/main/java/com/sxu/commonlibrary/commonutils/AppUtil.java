package com.sxu.commonlibrary.commonutils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;

import java.util.List;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/6/20
 */


public class AppUtil {

	public static int getVersionCode(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return 0;
	}

	public static String getVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return null;
	}

	/**
	 * 获取APP的进程名
	 * @param context
	 * @return
	 */
	public static String getProcessName(Context context) {
		int pid = Process.myPid();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo process : am.getRunningAppProcesses()) {
			if (process.pid == pid) {
				return process.processName;
			}
		}

		return null;
	}

	/**
	 * APP是否在运行中（前台或后台）
	 * @param context
	 * @return
	 */
	public static boolean appIsRunning(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(50);
		//100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
		for (ActivityManager.RunningTaskInfo info : list) {
			if (context.getPackageName().equals(info.topActivity.getPackageName())
					|| context.getPackageName().equals(info.baseActivity.getPackageName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * App是否在前台运行
	 * @param context
	 * @return
	 */
	public static boolean appIsForeground(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (!EmptyUtil.isEmpty(taskList) && context.getPackageName().equals(taskList.get(0).topActivity.getPackageName())) {
			return true;
		}

		return false;
	}

	public static boolean appIsInstalled(Application context, String packageName) {
		PackageManager pm = context.getPackageManager();
		if (pm != null) {
			List<PackageInfo> packageList = pm.getInstalledPackages(0);
			for (PackageInfo appInfo : packageList) {
				if (appInfo.packageName.equals(packageName)) {
					return true;
				}
			}
		}

		return false;
	}
}


