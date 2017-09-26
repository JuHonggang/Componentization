package com.sxu.commonbusiness.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sxu.commonlibrary.commonutils.LogUtil;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 分享工具类
 *
 * @author Freeman
 * @date 17/9/21
 */


public class ShareUtils {

	public final static int SHARE_BY_WECHAT = 1;
	public final static int SHARE_BY_MOMENT = 2;
	public final static int SHARE_BY_SINA = 3;

	public static void share(int flowId, String title, String desc, String iconUrl, String url) {
		switch (flowId) {
			case SHARE_BY_WECHAT:
				shareByWechat(title, desc, iconUrl, url);
				break;
			case SHARE_BY_MOMENT:
				shareByMoment(title, desc, iconUrl, url);
				break;
			case SHARE_BY_SINA:
				shareBySina(title, desc, iconUrl, url);
				break;
			default:
				break;
		}
	}

	private static void shareByWechat(String title, String desc, String iconUrl, String url) {

	}

	private static void shareByMoment(String title, String desc, String iconUrl, String url) {

	}

	private static void shareBySina(String title, String desc, String iconUrl, String url) {

	}

//	protected void handleResult(int requestCode, int resultCode, Intent data) {
//		// SSO 授权回调
//		if (ssoHandler != null) {
//			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//		}
//	}

//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
//		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
//		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
//		weiboApi.handleWeiboResponse(intent, this);
//	}
//
//	private void shareByWeibo() {
//		if (!TextUtils.isEmpty(url)) {
//			if (weiboApi.isWeiboAppInstalled()) {
//				if (!TextUtils.isEmpty(iconUrl)) {
//					ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
//						@Override
//						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//							super.onLoadingComplete(imageUri, view, loadedImage);
//							com.sina.weibo.sdk.utils.LogUtil.i("图片下载成功");
//							ShareByWeiBoProcess(loadedImage);
//						}
//
//						@Override
//						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//							super.onLoadingFailed(imageUri, view, failReason);
//							com.sina.weibo.sdk.utils.LogUtil.i("图片下载失败" + failReason.getCause());
//							ShapeDrawable drawable;
//							ShareByWeiBoProcess(BitmapUtils.drawableToBitmap(getResources()
//									.getDrawable(R.drawable.logo)));
//						}
//					});
//				} else {
//					ShareByWeiBoProcess(BitmapUtils.drawableToBitmap(getResources()
//							.getDrawable(R.drawable.logo)));
//				}
//			} else {
//				ToastUtil.show(this, "您还没有安装微博客户端哦~");
//				finish();
//			}
//		} else {
//			ToastUtil.show(this, "分享失败");
//			com.sina.weibo.sdk.utils.LogUtil.i("分享失败");
//			finish();
//		}
//	}
//
//	private void ShareByWeiBoProcess(Bitmap bitmap) {
//		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
//		TextObject textObject = new TextObject();
//		textObject.text = title + url;
//		ImageObject imageObject = new ImageObject();
//		imageObject.setImageObject(bitmap);
//		multiMessage.imageObject = imageObject;
//		multiMessage.textObject = textObject;
//		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//		// 用transaction唯一标识一个请求
//		request.transaction = String.valueOf(System.currentTimeMillis());
//		request.multiMessage = multiMessage;
//		// 3. 发送请求消息到微博，唤起微博分享界面
//		weiboApi.sendRequest(ShareActivity.this, request);
//	}
//
//	/**
//	 * 构造POST请求直接发送微博
//	 * @param token SSO认证的token
//	 */
//	private void shareImage(final String token) {
//		ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				super.onLoadingComplete(imageUri, view, loadedImage);
//				int size = loadedImage.getByteCount();
//				com.sina.weibo.sdk.utils.LogUtil.i("size===" + size);
//				Bitmap thumb = null;
//				if (size > SINA_SHAER_IMAGE_LIMIT) {
//					float rate = SINA_SHAER_IMAGE_LIMIT * 1.0f / size;
//					com.sina.weibo.sdk.utils.LogUtil.i("rate=="  + rate);
//					int newSize = (int)(loadedImage.getWidth() * rate);
//					thumb = Bitmap.createScaledBitmap(loadedImage, newSize, newSize, true);
//				} else {
//					thumb = loadedImage;
//				}
//				// 2. 调用接口发送微博
//				WeiboParameters params = new WeiboParameters(ShareConstants.APP_WEIBO_KEY);
//				params.put("access_token", token);
//				params.put("status",       title + url);
//				params.put("visible",      "0");
//				params.put("list_id",      "hello");
//				params.put("url", url);
//				params.put("pic",          thumb);
//				params.put("lat",          "14.5");
//				params.put("long",         "23.0");
//				params.put("annotations",  "");
//
//				AsyncWeiboRunner asyncWeiboRunner = new AsyncWeiboRunner(ShareActivity.this);
//				asyncWeiboRunner.requestAsync(
//						"https://api.weibo.com/2/statuses/upload.json",
//						params,
//						"POST",
//						new RequestListener() {
//							@Override
//							public void onComplete(String s) {
//								ToastUtil.show(ShareActivity.this, "分享成功");
//								ProgressDialog.hideMe();
//								finish();
//							}
//
//							@Override
//							public void onWeiboException(WeiboException e) {
//								com.sina.weibo.sdk.utils.LogUtil.i("图片分析失败" + e.getMessage());
//								ProgressDialog.hideMe();
//							}
//						});
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//				super.onLoadingFailed(imageUri, view, failReason);
//			}
//		});
//	}
//
//	/**
//	 * 微信分享（微信好友和朋友圈）
//	 *
//	 * @param flag 0表示分享到朋友圈， 1表示分享给微信好友
//	 */
//	private void shareByWechat(final int flag) {
//		if (!TextUtils.isEmpty(url)) {
//			if (wxApi.isWXAppInstalled()) {
//				if (!TextUtils.isEmpty(iconUrl)) {
//					ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
//						@Override
//						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//							super.onLoadingComplete(imageUri, view, loadedImage);
//							shareByWeChatProcess(flag, loadedImage);
//						}
//
//						@Override
//						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//							super.onLoadingFailed(imageUri, view, failReason);
//							shareByWeChatProcess(flag, BitmapUtils.drawableToBitmap(getResources()
//									.getDrawable(R.drawable.logo)));
//						}
//					});
//				} else {
//					shareByWeChatProcess(flag, BitmapUtils.drawableToBitmap(getResources()
//							.getDrawable(R.drawable.logo)));
//				}
//			} else {
//				ToastUtil.show(this, "您还没有安装微信客户端哦~");
//				finish();
//			}
//		} else {
//			ToastUtil.show(this, "分享失败");
//			com.sina.weibo.sdk.utils.LogUtil.i("分享失败");
//			finish();
//		}
//	}
//
//	private void shareByWeChatProcess(final int flag, Bitmap bitmap) {
//		WXWebpageObject webpage = new WXWebpageObject();
//		webpage.webpageUrl = url;
//		WXMediaMessage msg = new WXMediaMessage(webpage);
//		msg.title = title;
//		msg.description = desc;
//		msg.thumbData = IOConvertUtils.bmpToByteArray(scaleBitmap(bitmap), true);
//		SendMessageToWX.Req request = new SendMessageToWX.Req();
//		request.transaction = buildTransaction("webpage");
//		request.message = msg;
//		request.scene = (flag == 0) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//		wxApi.sendReq(request);
//		finish();
//	}
//
//	public Bitmap scaleBitmap(Bitmap bitMap) {
//		int width = bitMap.getWidth();
//		int height = bitMap.getHeight();
//		// 计算缩放比例
//		float scaleWidth = ((float) DEFAULT_SHARE_ICON_SIZE) / width;
//		float scaleHeight = ((float) DEFAULT_SHARE_ICON_SIZE) / height;
//		// 取得想要缩放的matrix参数
//		Matrix matrix = new Matrix();
//		matrix.postScale(scaleWidth, scaleHeight);
//		// 得到新的图片
//		Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
//
//		return newBitMap;
//	}
//
//	private String buildTransaction(final String type) {
//		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//	}
//
//	/**
//	 * 接收微客户端博请求的数据。
//	 * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
//	 *
//	 * @param baseResp 微博请求数据对象
//	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
//	 */
//	@Override
//	public void onResponse(BaseResponse baseResp) {
//		if (baseResp != null) {
//			switch (baseResp.errCode) {
//				case WBConstants.ErrorCode.ERR_OK:
//					ToastUtil.show(this, "分享成功");
//					break;
//				case WBConstants.ErrorCode.ERR_FAIL:
//					ToastUtil.show(this, "分享失败");
//					com.sina.weibo.sdk.utils.LogUtil.i("分享失败");
//					break;
//				default:
//					break;
//			}
//		}
//		finish();
//	}
//
//	/***
//	 * 实现WeiboAuthListener接口，返回授权结果
//	 * 通过access_token和expires_in获取accesstoken
//	 * @author Administrator
//	 *
//	 */
//	class AuthDialogListener implements WeiboAuthListener {
//
//		@Override
//		public void onCancel() {
//			LogUtil.i("微博授权取消");
//			finish();
//		}
//
//		@Override
//		public void onComplete(Bundle values) {
//			accessToken = Oauth2AccessToken.parseAccessToken(values);
//			com.sina.weibo.sdk.utils.LogUtil.i("微博授权完成" + accessToken);
//			if (accessToken.isSessionValid()) {
//				// 保存 Token 到 SharedPreferences
//				AccessTokenKeeper.writeAccessToken(ShareActivity.this, accessToken);
//				shareByWeibo();
//			} else {
//				com.sina.weibo.sdk.utils.LogUtil.i("微博开发账号签名错误");
//			}
//		}
//
//		@Override
//		public void onWeiboException(WeiboException e) {
//			com.sina.weibo.sdk.utils.LogUtil.i("微博授权失败" + e.getMessage());
//			finish();
//		}
//	}
}
