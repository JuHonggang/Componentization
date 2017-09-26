package com.sxu.commonlibrary.commonutils;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 判断数据结构是否为空
 *
 * @author Freeman
 * @date 17/6/20
 */


public class EmptyUtil {

	public static boolean isEmpty(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.size() == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}

		return false;
	}
}
