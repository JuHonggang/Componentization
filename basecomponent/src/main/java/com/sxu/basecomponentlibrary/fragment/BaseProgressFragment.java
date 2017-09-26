package com.sxu.basecomponentlibrary.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sxu.basecomponentlibrary.R;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.sxu.commonlibrary.datasource.http.bean.BaseBean;

/**
 * Created by juhg on 16/2/25.
 */
public abstract class BaseProgressFragment<T extends BaseBean> extends Fragment {

    public View rootView;
    public View loadingLayout;
    public ViewStub resultStub;
    public LinearLayout progressLayout;
    public FrameLayout containerLayout;

    // 用于区分第一次加载和刷新加载
    protected boolean isFirstLoad = true;
    /**
     * 数据请求失败或为空
     */
    protected final int MSG_LOAD_EMPTY = -1;
    /**
     * 数据请求成功
     */
    protected final int MSG_LOAD_FINISH = -2;
    /**
     * 数据请求成功
     */
    protected final int MSG_LOAD_REFRESH_FINISH = -3;
    /**
     * 加载更多数据
     */
    protected final int MSG_LOAD_MORE_FINISH = -4;
    /**
     * 刷新页面
     */
    protected final int MSG_REFRESH_FINISH = -5;
    /**
     * 没有更多数据
     */
    protected final int MSG_LOAD_NO_MORE = -6;
    /**
     * 没有登录
     */
    protected final int MSG_NO_LOGIN = -7;
    /**
     * 数据请求失败
     */
    protected final int MSG_LOAD_FAILURE = -8;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleMsg(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requestData();
        if (getLayoutResId() != 0) {
            rootView = inflater.inflate(getLayoutResId(), null);
        }
        View loadingLayout = inflater.inflate(R.layout.cl_fragment_progress_layout, null);
        resultStub = (ViewStub)loadingLayout.findViewById(R.id.base_result_stub);
        progressLayout = (LinearLayout) loadingLayout.findViewById(R.id.base_loading_layout);
        containerLayout = (FrameLayout)loadingLayout.findViewById(R.id.base_container_layout);

        return loadingLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
        resultStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    public abstract void requestData();

    public abstract int getLayoutResId();

    public abstract void getViews();

    public abstract void initFragment();

    public void notifyLoadFinish(int what) {
        handler.sendEmptyMessage(what);
    }

    protected void setCommonAdapter() {

    }

    protected void handleMsg(Message msg) {
        progressLayout.setVisibility(View.GONE);
        switch (msg.what) {
            case MSG_LOAD_EMPTY:
                resultStub.setVisibility(View.VISIBLE);
                //resultIcon.setImageResource(R.drawable.ic_launcher);
                //resultText.setText("没有搜索到结果");
                break;
            case MSG_LOAD_FINISH:
                isFirstLoad = false;
                resultStub.setVisibility(View.GONE);
                if (rootView != null) {
                    containerLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in));
                    containerLayout.addView(rootView);
                    initFragment();
                }
                break;
            case MSG_LOAD_REFRESH_FINISH:
                resultStub.setVisibility(View.GONE);
                initFragment();
                break;
            case MSG_LOAD_FAILURE:
                resultStub.setVisibility(View.VISIBLE);
                //resultIcon.setImageResource(R.drawable.ic_launcher);
                //resultText.setText("您好像没有连接网络");
                break;
            case MSG_LOAD_NO_MORE:
                resultStub.setVisibility(View.GONE);
                ToastUtil.show(getActivity(), "没有更多数据啦~");
                break;
            case MSG_NO_LOGIN:
                // 启动登录窗口
                break;
            default:
                break;
        }
    }
}
