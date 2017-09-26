package com.sxu.commonlibrary.datasource.http.impl;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/6/21
 */


import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.sxu.commonlibrary.datasource.http.bean.BaseBean;
import com.sxu.commonlibrary.datasource.http.bean.BaseProtocolBean;
import com.sxu.commonlibrary.datasource.http.util.MD5Util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by juhg on 16/2/17.
 */
public class OkHttpQuery<T extends BaseBean> {

	protected Context context;
	protected Class<T> TClass;
	protected OnQueryFinishListener queryFinishListener;
	protected Handler mainHandler;
	private BaseHttpClient httpClient;
	/**
	 * 请求结果
	 */
	public T queryResult;

	protected final int RESPONSE_CODE_DEFAULT = 100;        // 默认的应答code
	protected final int RESPONSE_CODE_EMPTY = 0;            // 数据请求为空
	protected final int RESPONSE_CODE_PARSE_ERROR = 2;      // 数据解析错误

	public OkHttpQuery(Context context, Class<T> TClass, OnQueryFinishListener<T> queryFinishListener) {
		this.context = context;
		this.TClass = TClass;
		this.mainHandler = new Handler(context.getMainLooper());
		this.queryFinishListener = queryFinishListener;
		this.httpClient = BaseHttpClient.getInstance();
	}

	private void asyncGet(String url, Map<String, String> param, Callback responseCallback) {
		url += "&sign=" + generateSign(url, param);
		Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
		// 追加请求的参数
		if (param != null) {
			Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
			while (iterator.hasNext()) {
				uriBuilder.appendQueryParameter(iterator.next().getKey(), iterator.next().getValue());
			}
		}

		String realUrl = uriBuilder.build().toString();
		Request request = new Request.Builder().url(realUrl).build();
		httpClient.newCall(request).enqueue(responseCallback);
	}

	private void asyncPost(String url, Map<String, String> param, Callback responseCallback) {
		FormBody.Builder formBuilder = new FormBody.Builder();
		if (param != null) {
			Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> item = iterator.next();
				formBuilder.add(item.getKey(), item.getValue());
			}
		}

		FormBody formBody = formBuilder.build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		httpClient.newCall(request).enqueue(responseCallback);
	}

	private void asyncPost(String url, byte[] data, Callback responseCallback) {

	}

	public void doGetQuery(String url) {
		doGetQuery(url, null);
	}

	public void doGetQuery(String url, Map<String, String> param) {
		//url += "&sign=" + generateSign(url, param);
		doGetQuery(url, param, new MyCallBack());
	}

	public void doGetQuery(String url, Map<String, String> param, Callback responseCallback) {
		asyncGet(url, param, responseCallback);
	}

	public void doPostQuery(String url, Map<String, String> param) {
		url += "&sign=" + generateSign(url, param);
		asyncPost(url, param, new MyCallBack());
	}

	public void doPostQuery(String url, byte[] data) {
		asyncPost(url, data, new MyCallBack());
	}

	private class MyCallBack implements Callback {
		@Override
		public void onResponse(Call call, Response response) throws IOException {
			final Response respon = response;
			final String content = respon.body().string();
			if (response.isSuccessful()) {
				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						if (!TextUtils.isEmpty(content)) {
							try {
								queryResult = BaseBean.fromJson(content, TClass);
								if (queryResult instanceof BaseProtocolBean) {
									if (((BaseProtocolBean)queryResult).code == 1) {
										queryFinishListener.onFinish(queryResult);
									} else {
										queryFinishListener.onError(((BaseProtocolBean) queryResult).code, ((BaseProtocolBean) queryResult).msg);
									}
								} else {
									queryFinishListener.onError(RESPONSE_CODE_PARSE_ERROR, "Bean对象没有继承BaseProtocolBean");
								}
							} catch (Exception e) {
								e.printStackTrace(System.out);
								queryFinishListener.onError(RESPONSE_CODE_PARSE_ERROR, "数据解析错误");
							}
						} else {
							queryFinishListener.onError(RESPONSE_CODE_EMPTY, "请求结果为空");
						}
					}
				});
			} else {
				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						queryFinishListener.onError(respon.code(), respon.message());
					}
				});
			}
		}

		@Override
		public void onFailure(Call call, IOException e) {
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					queryFinishListener.onError(RESPONSE_CODE_DEFAULT, "您好像没有连接网络哦");
				}
			});
		}
	}

	public interface OnQueryFinishListener<T extends BaseBean> {
		void onFinish(T bean);
		void onError(int errCode, String errMsg);
	}

//    private String generateSign(String url, Map<String, String> params) {
//        String publicKey = "de17e9920a527243c6e3874f65f5e0ac";
//        TreeSet<String> paramSet = new TreeSet<>();
//        if (!TextUtils.isEmpty(url)) {
//            if (url.contains("?")) {
//                String urlParam = url.split("[?]")[1];
//                String[] urlAllParams = urlParam.split("&");
//                if (urlAllParams != null) {
//                    for (int i = 0; i < urlAllParams.length; i++) {
//                        paramSet.add(urlAllParams[i]);
//                    }
//                }
//            }
//        }
//
//        if (params != null && params.size() > 0) {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                if (TextUtils.isEmpty(entry.getValue()) || entry.getValue().equals("null")) {
//                    paramSet.add(entry.getKey() + "=");
//                } else {
//                    paramSet.add(entry.getKey() + "=" + entry.getValue());
//                }
//            }
//        }
//
//        Iterator<String> iterator = paramSet.iterator();
//        StringBuffer paramPrefix = new StringBuffer();
//        while (iterator.hasNext()) {
//            paramPrefix.append("&");
//            paramPrefix.append(iterator.next());
//        }
//
//        return MD5Util.toMD5(publicKey + paramPrefix.substring(1).toString());
//    }

	private TreeSet<String> urlTreeSet = new TreeSet<String>();
	String[] paramStrings;
	String params;
	StringBuilder urlPre;

	protected String generateSign(String url, Map<String, String> param) {

		String secert = "";

		urlTreeSet.clear();
		urlPre = new StringBuilder();
		if (!TextUtils.isEmpty(url)) {
			if (url.contains("?")) {

				//分隔数组
				params = url.split("[?]")[1];

				if (!TextUtils.isEmpty(params)) {
					paramStrings = params.split("&");
					if (paramStrings != null && paramStrings.length > 0) {

						for (int i = 0; i < paramStrings.length; i++) {
							urlTreeSet.add(paramStrings[i]);
						}
					}
				}
			}
		}
		if (param != null && param.size() > 0) {//参数为空

			for (Map.Entry<String, String> entry : param.entrySet()) {
				if (TextUtils.isEmpty(entry.getValue()) || "null".equals(entry.getValue())) {
					urlTreeSet.add(entry.getKey() + "=");
				} else {

					urlTreeSet.add(entry.getKey() + "=" + entry.getValue());
				}
			}
		}

		Iterator it = urlTreeSet.iterator();

		boolean isFirstParam = true;
		while (it.hasNext()) {

			if (isFirstParam) {
				urlPre.append(it.next());
				isFirstParam = false;
			} else {
				urlPre.append("&").append(it.next());
			}

		}

		String returnString = MD5Util.toMD5(secert + urlPre.toString()) + "&mfr="
				+ URLEncoder.encode("");

		return returnString;
	}
}
