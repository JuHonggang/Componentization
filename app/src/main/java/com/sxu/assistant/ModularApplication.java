package com.sxu.assistant;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/19
 */


public class ModularApplication extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			ARouter.openLog();
			ARouter.openDebug();
		}
		ARouter.init(this);
	}
}
