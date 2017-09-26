package com.sxu.commonbusiness.launchguide.bean;

import android.content.Context;
import android.os.Build;

import com.sxu.commonlibrary.commonutils.DeviceUtil;
import com.sxu.commonlibrary.commonutils.DisplayUtil;
import com.sxu.commonlibrary.datasource.http.bean.BaseBean;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/22
 */


public class DeviceInfo extends BaseBean {

	public String version;
	public String networkType;
	public String imei;
	public String macAddress;
	public String channel;
	public String deviceToken;
	public String model;
	public String manufacturer;
	public String screenSize; // width*height
	public String appVersion;

	public DeviceInfo(Context context) {
		version = Build.VERSION.RELEASE;
		model = Build.BOARD + " " + Build.MODEL;
		manufacturer = Build.MANUFACTURER;
		networkType = "";
		imei = DeviceUtil.getDeviceId(context);
		macAddress = DeviceUtil.getMacAddress(context);
		deviceToken = DeviceUtil.getDeviceToken(context);
		screenSize = DisplayUtil.getScreenWidth() + "x" + DisplayUtil.getScreenHeight();
		appVersion = "";
		channel = "";
	}

	@Override
	public String toString() {
		return "DeviceInfo{" +
				"version='" + version + '\'' +
				", networkType='" + networkType + '\'' +
				", imei='" + imei + '\'' +
				", macAddress='" + macAddress + '\'' +
				", channel='" + channel + '\'' +
				", deviceToken='" + deviceToken + '\'' +
				", manufacturer='" + manufacturer + '\'' +
				", screenSize='" + screenSize + '\'' +
				", appVersion='" + appVersion + '\'' +
				'}';
	}
}
