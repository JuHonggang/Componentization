package com.sxu.commonbusiness.share;

import android.content.Intent;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 处理分享的结果
 *
 * @author Freeman
 * @date 17/9/25
 */


public interface ShareHandler {

	void handleResult(int requestCode, int resultCode, Intent intent);
}
