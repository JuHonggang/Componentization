package com.sxu.commonbusiness.share.instance;

import com.sxu.commonbusiness.share.ShareHandler;
import com.sxu.commonbusiness.share.ShareListener;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/25
 */


public abstract class ShareInstance extends ShareListener implements ShareHandler {

	/**
	 * 分享多媒体，包括文字，图片，视频，URL等
	 * @param title
	 * @param desc
	 * @param iconUrl
	 * @param url
	 */
	public abstract void onShare(String title, String desc, String iconUrl, String url);
}
