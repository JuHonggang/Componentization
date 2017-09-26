package com.sxu.commonbusiness.share.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonbusiness.share.ShareHandler;
import com.sxu.commonbusiness.share.ShareListener;
import com.sxu.commonlibrary.commonutils.BitmapUtil;
import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/9/25
 */


public class WeiboShareInstance extends ShareInstance implements ShareHandler {

	private IWeiboShareAPI weiboApi;
	private Oauth2AccessToken accessToken;
	private SsoHandler ssoHandler;
	private Activity activity;
	private final int SHARE_IMAGE_MAX_SIZE = 32 * 1024 * 8; // 分享的图片不能大于32K

	public void WeiboShareInstance(Activity activity) {
		this.activity = activity;
		weiboApi = WeiboShareSDK.createWeiboAPI(activity, ShareConstants.APP_WEIBO_KEY);
		weiboApi.registerApp();
		accessToken = AccessTokenKeeper.readAccessToken(activity);
	}

	public void onShare(final String title, final String desc, final String iconUrl, final String url, ShareListener listener) {
		if (accessToken != null && accessToken.isSessionValid()) {
			if (!TextUtils.isEmpty(iconUrl)) {
				ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						shareMediaByWeibo(activity, title, url, BitmapUtil.zoomBitmap(loadedImage, SHARE_IMAGE_MAX_SIZE));
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						super.onLoadingFailed(imageUri, view, failReason);
						shareMediaByWeibo(activity, title, url, null);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						super.onLoadingCancelled(imageUri, view);
						shareMediaByWeibo(activity, title, url, null);
					}
				});
			} else {
				shareMediaByWeibo(activity, title, url, null);
			}
		} else {
			AuthInfo authInfo = new AuthInfo(activity, ShareConstants.APP_WEIBO_KEY, ShareConstants.REDIRECT_URL,
					ShareConstants.SCOPE);
			ssoHandler = new SsoHandler(activity, authInfo);
			ssoHandler.authorize(new AuthDialogListener());
		}
	}

	private void shareMediaByWeibo(Activity activity, String title, String url, Bitmap bitmap) {
		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
		TextObject textObject = new TextObject();
		textObject.text = title + url;
		if (bitmap != null && !bitmap.isRecycled()) {
			ImageObject imageObject = new ImageObject();
			imageObject.setImageObject(bitmap);
			multiMessage.imageObject = imageObject;
		}
		multiMessage.textObject = textObject;
		requestShare(activity, multiMessage);
	}


	public void requestShare(Activity activity, WeiboMultiMessage message) {
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = message;
		weiboApi.sendRequest(activity, request);
	}

	@Override
	public void handleResult(int requestCode, int resultCode, Intent intent) {
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, intent);
		} else {
			weiboApi.handleWeiboResponse(intent, this);
		}
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

	/***
	 * 实现WeiboAuthListener接口，返回授权结果
	 * 通过access_token和expires_in获取accesstoken
	 * @author Administrator
	 *
	 */
	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			LogUtil.i("微博授权取消");
			activity.finish();
		}

		@Override
		public void onComplete(Bundle values) {
			accessToken = Oauth2AccessToken.parseAccessToken(values);
			LogUtil.i("微博授权完成" + accessToken);
			if (accessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(activity, accessToken);
				onShare("", "", "", "", null);
			} else {
				LogUtil.i("微博开发账号签名错误");
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			LogUtil.i("微博授权失败" + e.getMessage());
			activity.finish();
		}
	}
}
