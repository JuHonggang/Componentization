package com.sxu.commonbusiness.share;

import android.app.Activity;

import com.sxu.commonbusiness.share.instance.QQShareInstance;
import com.sxu.commonbusiness.share.instance.ShareInstance;
import com.sxu.commonbusiness.share.instance.WechatShareInstance;
import com.sxu.commonbusiness.share.instance.WeiboShareInstance;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/25
 */


public class ShareManager {

	private String title;
	private String desc;
	private String iconUrl;
	private String url;
	private Activity activity;

	private static ShareManager instance;
	private ShareInstance shareInstance = null;

	private ShareManager() {

	}

	public static ShareManager getInstance() {
		if (instance == null) {
			instance = new ShareManager();
		}

		return instance;
	}

	public void init(Activity activity, String title, String desc, String iconUrl, String url) {
		this.activity = activity;
		this.title = title;
		this.desc = desc;
		this.iconUrl = iconUrl;
		this.url = url;
	}

	public void share(int flowId) {
		switch (flowId) {
			case ShareConstants.SHARE_BY_WECAHT:
				shareInstance = new WechatShareInstance(activity, ShareConstants.SHARE_BY_WECAHT);
				break;
			case ShareConstants.SHARE_BY_WECHAT_MOMENT:
				shareInstance = new WechatShareInstance(activity, ShareConstants.SHARE_BY_WECHAT_MOMENT);
				break;
			case ShareConstants.SHARE_BY_WEIBO:
				shareInstance = new WeiboShareInstance(activity);
				break;
			case ShareConstants.SHARE_BY_QQ:
				shareInstance = new QQShareInstance(activity, ShareConstants.SHARE_BY_QQ);
				break;
			case ShareConstants.SHARE_BY_QQ_ZONE:
				shareInstance = new QQShareInstance(activity, ShareConstants.SHARE_BY_QQ_ZONE);
				break;
			default:
				break;
		}

		if (shareInstance != null) {
			shareInstance.onShare(title, desc, iconUrl, url);
		}
	}

	public ShareInstance getShareInstance() {
		return shareInstance;
	}
}
