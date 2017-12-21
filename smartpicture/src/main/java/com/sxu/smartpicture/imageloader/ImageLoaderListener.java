package com.sxu.smartpicture.imageloader;

import android.graphics.Bitmap;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 2017/12/5
 */


public interface ImageLoaderListener {

	void onStart();

	void onProcess(int completedSize, int totalSize);

	void onCompleted(Bitmap bitmap);

	void onFailure(Exception e);
}
