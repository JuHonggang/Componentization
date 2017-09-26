package com.sxu.assistant.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.assistant.R;
import com.sxu.assistant.fragment.HomeFragment;
import com.sxu.basecomponentlibrary.activity.BaseActivity;
import com.sxu.commonlibrary.commonutils.ToastUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	private ViewPager viewPager;
	private RadioButton tabRadios[] = new RadioButton[4];

	private int currentPage;
	private long exitTime = 0;
	private String[] tabTitle = {"首页", "兴趣", "消息", "我的"};
	private Fragment[] tabFragment = new Fragment[4];

	@Override
	protected int getLayoutResId() {
		overridePendingTransition(0, 0);
		return R.layout.activity_main;
	}

	@Override
	protected void getViews() {
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		tabRadios[0] = (RadioButton)findViewById(R.id.tab_home_radio);
		tabRadios[1] = (RadioButton)findViewById(R.id.tab_find_radio);
		tabRadios[2] = (RadioButton)findViewById(R.id.tab_msg_radio);
		tabRadios[3] = (RadioButton)findViewById(R.id.tab_my_radio);
	}

	@Override
	protected void initActivity() {
		viewPager.setOffscreenPageLimit(4);
		viewPager.setCurrentItem(0);
		tabRadios[0].setChecked(true);
		viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager()));

		tabRadios[0].setOnClickListener(this);
		tabRadios[1].setOnClickListener(this);
		tabRadios[2].setOnClickListener(this);
		tabRadios[3].setOnClickListener(this);

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				tabRadios[position].setChecked(true);
				currentPage = position;
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		int page = intent.getIntExtra("page", 0);
		viewPager.setCurrentItem(page, false);
		tabRadios[page].setChecked(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tab_home_radio:
				ARouter.getInstance()
						.build("/ab/a2")
						.navigation();
				viewPager.setCurrentItem(0, false);
				break;
			case R.id.tab_find_radio:
				viewPager.setCurrentItem(1, false);
				break;
			case R.id.tab_msg_radio:
				viewPager.setCurrentItem(2, false);
				break;
			case R.id.tab_my_radio:
				viewPager.setCurrentItem(3, false);
				break;
			default:
				break;
		}
	}

	public static void enter(Context context, int page) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("page", page);
		context.startActivity(intent);
	}

	private class TabFragmentAdapter extends FragmentPagerAdapter {

		public TabFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return tabTitle.length;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					tabFragment[position] = new HomeFragment();
					break;
				case 1:
					tabFragment[position] = new Fragment();
					break;
				case 2:
					tabFragment[position] = new Fragment();
					break;
				case 3:
					tabFragment[position] = new Fragment();
					break;
				default:
					break;
			}

			return tabFragment[position];
		}
	}

	@Override
	public void onBackPressed() {
		if (currentPage != 0) {
			viewPager.setCurrentItem(0, false);
		} else {
			if (exitTime != 0 && System.currentTimeMillis() - exitTime < 2000) {
				super.onBackPressed();
			} else {
				ToastUtil.show(this, "再按一次返回将退出应用");
				exitTime = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}
}
