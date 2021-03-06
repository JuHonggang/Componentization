package com.sxu.smartpicture.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 2017/12/5
 */

public interface ImageLoaderInstance {

	void init(Context context);

	void displayImage(String url, WrapImageView imageView);

	void displayImage(String url, WrapImageView imageView, final ImageLoaderListener listener);

	void downloadImage(Context context, String url, ImageLoaderListener listener);

	void destroy();
}
