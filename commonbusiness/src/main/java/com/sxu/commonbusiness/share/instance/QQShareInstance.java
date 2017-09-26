package com.sxu.commonbusiness.share.instance;

import android.content.Intent;

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


public class QQShareInstance extends ShareInstance implements ShareHandler {

	@Override
	public void onShare(String title, String desc, String iconUrl, String url, ShareListener listener) {

	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {

	}

	@Override
	public void shareSuccess() {

	}

	@Override
	public void shareFailure(Exception e) {

	}

	@Override
	public void shareCancel() {

	}
}
