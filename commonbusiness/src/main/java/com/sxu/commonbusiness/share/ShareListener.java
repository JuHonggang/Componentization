package com.sxu.commonbusiness.share;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 分享结果监听器
 *
 * @author Freeman
 * @date 17/9/25
 */


public abstract class ShareListener implements IWXAPIEventHandler, IWeiboHandler.Response,  IUiListener{

	/**
	 * 微信分享回调
	 */
	@Override
	public void onReq(BaseReq baseReq) {

	}


	@Override
	public void onResp(BaseResp baseResp) {
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				shareSuccess();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				shareCancel();
				break;
			default:
				shareFailure(new Exception(baseResp.errStr));
				break;
		}
	}

	/**
	 * 微博分享回调
	 * @param baseResponse
	 */
	@Override
	public void onResponse(BaseResponse baseResponse) {
		switch (baseResponse.errCode) {
			case WBConstants.ErrorCode.ERR_OK:
				shareSuccess();
				break;
			case WBConstants.ErrorCode.ERR_FAIL:
				shareFailure(new Exception(baseResponse.errMsg));
				break;
			case WBConstants.ErrorCode.ERR_CANCEL:
				shareCancel();
				break;
			default:
				break;
		}
	}

	/**
	 * QQ分享回调
	 * @param o
	 */
	@Override
	public void onComplete(Object o) {
		shareSuccess();
	}

	@Override
	public void onError(UiError uiError) {
		shareFailure(new Exception(new StringBuilder("errorCode:").append(uiError.errorCode)
				.append(" errorMsg:").append(uiError.errorMessage)
				.append(" detailMsg:").append(uiError.errorDetail).toString()));
	}

	@Override
	public void onCancel() {
		shareCancel();
	}

	/**
	 * 由于不同的平台的分享回调不同，所以添加以下三个方法便于统一回调结果的通知
	 */
	public abstract void shareSuccess();

	public abstract void shareFailure(Exception e);

	public abstract void shareCancel();
}
