package com.sxu.basecomponentlibrary.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxu.basecomponentlibrary.R;
import com.sxu.commonlibrary.commonutils.DisplayUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.sxu.commonlibrary.datasource.http.bean.BaseBean;
import com.sxu.commonlibrary.uiwidget.NavigationBar;

/**
 * Created by juhg on 16/2/17.
 */
public abstract class BaseProgressActivity<T extends BaseBean> extends Activity {

    /**
     * 获取屏幕宽度，便于计算View的宽高
     */
    protected int screenWidth;
    /**
     * 数据请求失败或为空
     */
    protected static final int MSG_LOAD_EMPTY = -1;
    /**
     * 数据请求成功
     */
    protected static final int MSG_LOAD_FINISH = -2;
    /**
     * 加载更多数据
     */
    protected static final int MSG_LOAD_MORE_FINISH = -3;
    /**
     * 刷新页面
     */
    protected static final int MSG_REFRESH_FINISH = -4;
    /**
     * 没有更多数据
     */
    protected static final int MSG_LOAD_NO_MORE = -5;
    /**
     * 没有登录
     */
    protected static final int MSG_NO_LOGIN = -6;
    /**
     * 数据请求失败
     */
    protected static final int MSG_LOAD_FAILURE = -7;

    /**
     * Loading页面的子View
     */
    private TextView resultText;
    private ImageView noNetworkIcon;
    private ViewStub resultStub;
    private LinearLayout loadingLayout;
    private FrameLayout containerLayout;
    protected NavigationBar navigationBar;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleMsg(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        screenWidth = DisplayUtil.getScreenWidth();
        // 设置Actvity的切换动画
        overridePendingTransition(R.anim.anim_push_right_in, R.anim.anim_push_left_out);
        requestData();
        // 对于启动时进行数据请求的页面，先加载"加载中"页面
        setContentView(R.layout.cl_activity_progress_layout);
        navigationBar = (NavigationBar)findViewById(R.id.base_navigate_layout);
        containerLayout = (FrameLayout)findViewById(R.id.base_container_layout);
        loadingLayout = (LinearLayout) findViewById(R.id.base_loading_layout);
        resultStub = (ViewStub)findViewById(R.id.base_result_stub);
        noNetworkIcon = (ImageView)findViewById(R.id.no_network_icon);
        resultText = (TextView)findViewById(R.id.base_tips_text);

        navigationBar.setTitle("加载中");
        resultStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }


    /**
     * 获取Activity的布局ID
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 请求网络数据
     */
    protected void requestData() {

    }

    /**
     * 获取Activity中所有的View
     */
    protected abstract void getViews();

    /**
     * Activity的逻辑实现
     */
    protected abstract void initActivity();

    public void notifyLoadFinish(int what) {
        handler.sendEmptyMessage(what);
    }

    protected void handleMsg(Message msg) {
        loadingLayout.setVisibility(View.GONE);
        switch (msg.what) {
            case MSG_LOAD_EMPTY:
                navigationBar.setTitle("加载失败");
                resultStub.setVisibility(View.VISIBLE);
                //noNetworkIcon.setImageResource(R.drawable.ic_launcher);
                resultText.setText("没有搜索到结果");
                break;
            case MSG_LOAD_FINISH:
                if (getLayoutResId() != 0) {
                    //containerLayout.removeAllViews();
                    resultStub.setVisibility(View.GONE);
                    containerLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
                    getLayoutInflater().inflate(getLayoutResId(), containerLayout);
                    //setContentView(getLayoutResId());
                    getViews();
                    initActivity();
                }
                break;
            case MSG_REFRESH_FINISH:
                break;
            case MSG_LOAD_FAILURE:
                navigationBar.setTitle("加载失败");
                resultStub.setVisibility(View.VISIBLE);
                //noNetworkIcon.setImageResource(R.drawable.ic_launcher);
                resultText.setText("您好像没有连接网络");
                break;
            case MSG_LOAD_NO_MORE:
                ToastUtil.show(this, "没有更多数据啦~");
                break;
            case MSG_NO_LOGIN:
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_push_left_in, R.anim.anim_push_right_out);
    }
}
