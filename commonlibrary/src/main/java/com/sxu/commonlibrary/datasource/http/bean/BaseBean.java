package com.sxu.commonlibrary.datasource.http.bean;

import com.google.gson.Gson;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/6/21
 */

public abstract class BaseBean {
	public String toJson() {
		return toJson(this);
	}

	public static String toJson(BaseBean bean) {
		if (bean != null) {
			return new Gson().toJson(bean);
		}

		return null;
	}

	public static <T extends BaseBean> T fromJson(String jsonStr,
	                                              Class<? extends BaseBean> subClass) {
		BaseBean newObj = new Gson().fromJson(jsonStr,
				subClass);

		return (T) newObj;

	}

	public String toString() {
		return toJson();
	}
}
