package com.sxu.commonbusiness.setting;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxu.basecomponentlibrary.activity.BaseActivity;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.launchguide.activity.GuideActivity;
import com.sxu.commonlibrary.commonutils.DisplayUtil;
import com.sxu.commonlibrary.uiwidget.NavigationBar;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity {

	private NavigationBar navigationBar;
	private LinearLayout contentLayout;

	private String[][] menus = { // 使用二维数组 便于设置分组之间的间距
			{"意见反馈", "清楚缓存", "版本升级"},
			{"使用帮助", "评价", "分享", "服务条款", "关于"},
			{"退出登录"}
	};

	private List<Intent> intentList = new ArrayList<>();

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
		initIntentList();
		loadItemLayout();
	}

	private void initIntentList() {
		intentList.add(new Intent(mContext, GuideActivity.class));
		intentList.add(new Intent(mContext, ClearCacheActivity.class));
		intentList.add(new Intent(mContext, CheckVersionActivity.class));

		intentList.add(new Intent(mContext, UseHelpActivity.class));
		intentList.add(new Intent(mContext, GotoAppMarketActivity.class));
		intentList.add(new Intent(mContext, ShareAppActivity.class));
		intentList.add(new Intent(mContext, ServiceProtocolActivity.class));
		intentList.add(new Intent(mContext, AboutAppActivity.class));

		intentList.add(new Intent(mContext, ExitLoginActivity.class));
	}

	private void loadItemLayout() {
		int screenWidth = DisplayUtil.getScreenWidth();
		int itemHeight = DisplayUtil.dpToPx(48);
		LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(screenWidth, itemHeight);
		LinearLayout.LayoutParams groupParams = new LinearLayout.LayoutParams(screenWidth, itemHeight);
		groupParams.topMargin = DisplayUtil.dpToPx(24);
		for (int i = 0; i < menus.length; i++) {
			for (int j = 0; j < menus[i].length; j++) {
				View itemView = LayoutInflater.from(mContext).inflate(R.layout.cb_item_setting_layout, null);
				((TextView) itemView.findViewById(R.id.item_text)).setText(menus[i][j]);
				final int index = i;
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(intentList.get(index));
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
