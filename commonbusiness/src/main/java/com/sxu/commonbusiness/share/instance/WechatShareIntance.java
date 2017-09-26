package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonbusiness.share.ShareHandler;
import com.sxu.commonbusiness.share.ShareListener;
import com.sxu.commonlibrary.commonutils.BitmapUtil;
import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
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


public class WechatShareIntance extends ShareInstance implements ShareHandler {

	private int flowId;
	private Activity activity;
	private IWXAPI wxApi;

	private final int SHARE_IMAGE_MAX_SIZE = 32 * 1024 * 8; // 分享的图片不能大于32K

	public void WechatShareIntance(Activity activity, int flowId) {
		this.flowId = flowId;
		this.activity = activity;
		wxApi = WXAPIFactory.createWXAPI(activity, ShareConstants.APP_WECHAT_KEY, true);
		wxApi.registerApp(ShareConstants.APP_WECHAT_KEY);
	}

	public void onShare(final String title, final String desc, final String iconUrl, final String url, ShareListener listener) {
		if (!TextUtils.isEmpty(url)) {
			if (wxApi.isWXAppInstalled()) {
				if (!TextUtils.isEmpty(iconUrl)) {
					ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							super.onLoadingComplete(imageUri, view, loadedImage);
							shareByWeChat(title, desc, iconUrl, BitmapUtil.zoomBitmap(loadedImage, SHARE_IMAGE_MAX_SIZE));
						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
							super.onLoadingFailed(imageUri, view, failReason);
							shareByWeChat(title, desc, iconUrl, null);
						}
					});
				} else {
					shareByWeChat(title, desc, iconUrl, null);
				}
			} else {
				listener.shareFailure(new Exception("您还没有安装微信客户端哦~"));
				activity.finish();
			}
		} else {
			listener.shareFailure(new Exception("url为空"));
			activity.finish();
		}
	}

	private void shareByWeChat(String title, String desc, String url, Bitmap bitmap) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = desc;
		//msg.thumbData = IOConvertUtils.bmpToByteArray(scaleBitmap(bitmap), true);
		SendMessageToWX.Req request = new SendMessageToWX.Req();
		request.transaction = buildTransaction("webpage");
		request.message = msg;
		request.scene = (flowId == 0) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(request);
		activity.finish();
	}


	public void requestShare(WXMediaMessage message, String transaction) {
		SendMessageToWX.Req request = new SendMessageToWX.Req();
		request.transaction = transaction;
		request.message = message;
		request.scene = (flowId == 0) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(request);
		activity.finish();
	}

	private String buildTransaction(final String type) {
		return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {

	}

	@Override
	public void shareSuccess() {
		ToastUtil.show(activity, "分享成功");
	}

	@Override
	public void shareFailure(Exception e) {
		ToastUtil.show(activity, "分享失败");
	}

	@Override
	public void shareCancel() {
		ToastUtil.show(activity, "取消分享");
	}
}
