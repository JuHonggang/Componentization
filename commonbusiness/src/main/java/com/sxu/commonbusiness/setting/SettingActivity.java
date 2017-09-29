package com.sxu.commonbusiness.setting;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sxu.basecomponentlibrary.activity.BaseActivity;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.share.activity.ShareActivity;
import com.sxu.commonlibrary.commonutils.DisplayUtil;
import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.sxu.commonlibrary.uiwidget.NavigationBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingActivity extends BaseActivity {

	private NavigationBar navigationBar;
	private LinearLayout contentLayout;

	private String[][] menus = { // 使用二维数组 便于设置分组之间的间距
			{"意见反馈", "清除缓存", "版本升级"},
			{"使用帮助", "评价", "分享", "服务条款", "关于"},
			{"退出登录"}
	};

	private List<Object> listenerList = new ArrayList<>();

	@Override
	protected int getLayoutResId() {
		return R.layout.cb_activity_setting_layout;
	}

	@Override
	protected void getViews() {
		navigationBar = (NavigationBar) findViewById(R.id.navigate_layout);
		contentLayout = (LinearLayout) findViewById(R.id.content_layout);
	}

	@Override
	protected void initActivity() {
		navigationBar.setTitle("设置");
		initListener();
		loadItemLayout();
	}

	private void initListener() {
		listenerList.add(new Intent(mContext, FeedbackActivity.class));
		listenerList.add(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 清除缓存
				ToastUtil.show(mContext, "清除缓存");
			}
		});
		listenerList.add(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 版本升级
				//ToastUtil.show(mContext, "版本升级");
				Toast.makeText(mContext, "版本升级", Toast.LENGTH_SHORT).show();
			}
		});

		listenerList.add(new Intent(mContext, UseHelpActivity.class));
		listenerList.add(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 去APP市场评价
				ToastUtil.show(mContext, "去APP市场评价");
			}
		});
		listenerList.add(new Intent(mContext, ShareActivity.class));
		listenerList.add(new Intent(mContext, ServiceProtocolActivity.class));
		listenerList.add(new Intent(mContext, AboutAppActivity.class));

		listenerList.add(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 退出登录
				ToastUtil.show(mContext, "退出登录");
			}
		});
	}

	private void loadItemLayout() {
		int screenWidth = DisplayUtil.getScreenWidth();
		int itemHeight = DisplayUtil.dpToPx(48);
		LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(screenWidth, itemHeight);
		LinearLayout.LayoutParams groupParams = new LinearLayout.LayoutParams(screenWidth, itemHeight);
		groupParams.topMargin = DisplayUtil.dpToPx(24);
		int childIndex = 0;
		for (int i = 0; i < menus.length; i++) {
			for (int j = 0; j < menus[i].length; j++) {
				View itemView = LayoutInflater.from(mContext).inflate(R.layout.cb_item_setting_layout, null);
				((TextView) itemView.findViewById(R.id.item_text)).setText(menus[i][j]);
				final int index = childIndex++;;
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						LogUtil.i("index===" + index);
						if (listenerList.get(index) instanceof Intent) {
							startActivity((Intent) listenerList.get(index));
						} else {
							((View.OnClickListener)listenerList.get(index)).onClick(v);
						}
					}
				});

				if (j == 0) {
					contentLayout.addView(itemView, groupParams);
				} else {
					contentLayout.addView(itemView, childParams);
				}
			}
		}
	}
}
