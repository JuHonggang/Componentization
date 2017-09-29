package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonbusiness.share.ShareHandler;
import com.sxu.commonbusiness.share.ShareListener;
import com.sxu.commonlibrary.commonutils.BitmapUtil;
import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/25
 */


public class WechatShareInstance extends ShareInstance {

	private int flowId;
	private Activity activity;
	private IWXAPI wxApi;

	private final int TEXT_MAX_LEN = 140; // 分享文字的最大长度
	private final int SHARE_IMAGE_MAX_SIZE = 32 * 1024 * 8; // 分享的图片不能大于32K

	public WechatShareInstance(Activity activity, int flowId) {
		this.flowId = flowId;
		this.activity = activity;
		if (!ImageLoader.getInstance().isInited()) {
			ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(activity);
			ImageLoader.getInstance().init(configuration);
		}
		wxApi = WXAPIFactory.createWXAPI(activity, ShareConstants.APP_WECHAT_KEY, true);
		wxApi.registerApp(ShareConstants.APP_WECHAT_KEY);
	}

	public void onShare(final String title, String desc, final String iconUrl, final String url) {
		if (wxApi.isWXAppInstalled()) {
			if (desc != null &&desc.length() > TEXT_MAX_LEN) {
				desc = desc.substring(0, TEXT_MAX_LEN);
			}
			final String content = desc;
			if (!TextUtils.isEmpty(iconUrl)) {
				ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						shareByWeChat(title, content, url, BitmapUtil.zoomBitmap(loadedImage, SHARE_IMAGE_MAX_SIZE));
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						super.onLoadingFailed(imageUri, view, failReason);
						shareByWeChat(title, content, url, null);
					}
				});
			} else {
				shareByWeChat(title, content, url, null);
			}
		} else {
			shareFailure(new Exception("您还没有安装微信客户端哦~"));
		}
	}

	private void shareByWeChat(String title, String desc, String url, Bitmap bitmap) {
		String transaction = null;
		StringBuilder builder = new StringBuilder();
		WXMediaMessage msg = null;
		if (!TextUtils.isEmpty(title)) {
			builder.append(title);
		}
		if (!TextUtils.isEmpty(url)) {
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = url;
			transaction = "webpage";
			msg = new WXMediaMessage(webpage);
			builder.append(" ").append(url);
		} else {
			msg = new WXMediaMessage();
			if (bitmap != null && !bitmap.isRecycled()) {
				transaction = "image";
				msg.mediaObject = new WXImageObject(bitmap);
			} else {
				transaction = "text";
				msg.mediaObject = new WXTextObject(title);
			}
		}

		msg.title = builder.toString();
		msg.description = desc;
		msg.thumbData = BitmapUtil.bitmapToByteArray(bitmap, true);
		requestShare(msg, transaction);
	}

	public void requestShare(WXMediaMessage message, String transaction) {
		SendMessageToWX.Req request = new SendMessageToWX.Req();
		request.transaction = transaction;
		request.message = message;
		request.scene = (flowId == ShareConstants.SHARE_BY_WECHAT_MOMENT) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(request);
	}

	private String buildTransaction(final String type) {
		return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		LogUtil.i("handeResult");
		//wxApi.handleIntent(intent, this);
	}

	@Override
	public void shareSuccess() {
		LogUtil.i("shareSuccess");
		ToastUtil.show(activity, "分享成功");
	}

	@Override
	public void shareFailure(Exception e) {
		LogUtil.i("shareFailure");
		ToastUtil.show(activity, "分享失败");
	}

	@Override
	public void shareCancel() {
		LogUtil.i("shareCancel");
		ToastUtil.show(activity, "取消分享");
	}
}
