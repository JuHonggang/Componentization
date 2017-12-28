package com.sxu.commonbusiness.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.sxu.basecomponentlibrary.manager.ThreadPoolManager;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author Freeman
 * @date 2017/12/22
 */

public class PayManager implements Handler.Callback {

	private volatile static PayManager instance;
	private IWXAPI mWxApi;
	private Handler mHandler;
	private AliPayListener mListener;

	private final static int MSG_WHAT_ALIPAY = 1001;

	private PayManager(Context context) {
		mWxApi = WXAPIFactory.createWXAPI(context, null);
		mHandler = new Handler(context.getMainLooper());
	}

	public static PayManager getInstance(Context context) {
		if (instance == null) {
			synchronized (PayManager.class) {
				if (instance == null) {
					instance = new PayManager(context);
				}
			}
		}

		return instance;
	}

	public boolean payByWeChat(PayRequestBean requestInfo, String orderId) {
		if (requestInfo != null && !TextUtils.isEmpty(orderId)) {
			PayReq req = new PayReq();
			req.appId = requestInfo.appId;
			req.nonceStr = requestInfo.nonceStr;
			req.packageValue = requestInfo.packageValue;
			req.partnerId = requestInfo.partnerId;
			req.prepayId = requestInfo.prepayId;
			req.sign = requestInfo.sign;
			req.timeStamp = requestInfo.timeStamp;
			req.extData = orderId;
			mWxApi.registerApp(requestInfo.appId);
			return mWxApi.sendReq(req);
		}

		return false;
	}

	public void payByAliPay(final Activity context, final String requestStr, AliPayListener listener) {
		this.mListener = listener;
		ThreadPoolManager.executeTask(new Runnable() {
			@Override
			public void run() {
				PayTask aliPay = new PayTask(context);
				String result = aliPay.pay(requestStr, true);
				Message msg = Message.obtain();
				msg.what = MSG_WHAT_ALIPAY;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		});
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == MSG_WHAT_ALIPAY) {
			if (mListener != null) {
				PayResultBean payResultBean = new PayResultBean((String)msg.obj);
				String resultStatus = payResultBean.getResultStatus();
				if (TextUtils.equals(resultStatus, "9000")) {
					mListener.onSuccess();
				} else {
					mListener.onFailure(new Exception(resultStatus));
				}
			}
		}

		return false;
	}

	public interface AliPayListener {
		void onSuccess();
		void onFailure(Exception e);
	}

}
