package com.sxu.basecomponentlibrary.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.sxu.basecomponentlibrary.R;

/**
 * 启动时不进行网络操作的Activity的基类
 *
 * Created by juhg on 16/2/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置Activity的切换动画
        overridePendingTransition(R.anim.anim_push_right_in, R.anim.anim_push_left_out);
        mContext = this;
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
            getViews();
        }
        initActivity();
    }

    /**
     * 获取Activity加载的布局ID
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 获取布局中的所有控件
     */
    protected abstract void getViews();

    /**
     * Activity的逻辑处理过程
     */
    protected abstract void initActivity();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 重写原因：
     *    FragmentActivity中的onSaveInstanceState默认会保存其包含的所有Fragment的状态，然而，当用户按下Home键后，
     * 系统可能由于内存不足等原因销毁此Activity，当再次返回到此Activity后，会重建Activity，而Fragment却还是销毁前的
     * 状态，当Fragment和Activity进行通信时，getActivity()为空，导致程序Crash.
     *
     * 解决方案：
     *    通过重写onSaveInstanceState，使程序退到后台后不保存Fragment的状态.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_push_left_in, R.anim.anim_push_right_out);
    }
}
