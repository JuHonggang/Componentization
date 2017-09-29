package com.sxu.commonbusiness.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sxu.commonbusiness.share.ShareConstants;
import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxApi =  WXAPIFactory.createWXAPI(this, ShareConstants.APP_WECHAT_KEY, false);
        wxApi.handleIntent(getIntent(), this);
        LogUtil.i("==============onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LogUtil.i("==============newIntent");
        wxApi.handleIntent(intent, this);
    }

    //微信发送消息给app，app接受并处理的回调函数
    @Override
    public void onReq(BaseReq req) {
        LogUtil.i("==============" + req.getType());
    }

    //app发送消息给微信，微信返回的消息回调函数,根据不同的返回码来判断操作是否成功
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.i("==============" + resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                ToastUtil.show(this, "分享成功");
                finish();
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                ToastUtil.show(this, "分享失败");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtil.show(this, "取消分享");
                break;
            default:
                finish();
                break;
        }
    }
}