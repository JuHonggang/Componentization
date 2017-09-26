package com.sxu.commonlibrary.datasource.http.impl;

import okhttp3.OkHttpClient;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/6/21
 */


public class BaseHttpClient extends OkHttpClient {

	private BaseHttpClient() {

	}

	public static BaseHttpClient getInstance() {
		return SingletonHolder.instance;
	}

	public static class SingletonHolder {
		private static final BaseHttpClient instance = new BaseHttpClient();
	}
}
