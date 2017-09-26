package com.sxu.commonlibrary.uiwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxu.commonlibrary.R;

/**
 * Created by Freeman on 17/4/18.
 */

public class NavigationBar extends FrameLayout {

	private View bottomLine;
	private View rootLayout;
	private TextView titleText;
	private TextView leftText;
	private TextView rightText;
	private ImageView backIcon;
	private ImageView rightIcon;
	private ImageView rightIcon2;
	private ImageView rightIcon3;
	
	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.cl_view_navigation_bar_layout, this);
		bottomLine = findViewById(R.id.nav_bottom_line);
		rootLayout = findViewById(R.id.root_layout);
		titleText = (TextView) findViewById(R.id.title_text);
		leftText = (TextView) findViewById(R.id.left_text);
		rightText = (TextView) findViewById(R.id.right_text);
		backIcon = (ImageView) findViewById(R.id.back_icon);
		rightIcon = (ImageView) findViewById(R.id.right_icon);
		rightIcon2 = (ImageView) findViewById(R.id.right_icon_2);
		rightIcon3 = (ImageView) findViewById(R.id.right_icon_3);

		backIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				((Activity)getContext()).onBackPressed();
			}
		});
	}
	public NavigationBar setBackOnClickListener(OnClickListener listener){
		backIcon.setOnClickListener(listener);
		return this;
	}

	public NavigationBar hideBackIcon() {
		backIcon.setVisibility(GONE);
		return this;
	}

	public NavigationBar showBackIcon() {
		backIcon.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar setBackIconResource(int resId) {
		backIcon.setImageResource(resId);
		return this;
	}

	public NavigationBar hideBottomLine() {
		bottomLine.setVisibility(GONE);
		return this;
	}

	public NavigationBar showBottomLine() {
		bottomLine.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar showBottomLineAndColor(int color) {
		bottomLine.setVisibility(VISIBLE);
		bottomLine.setBackgroundColor(color);
		return this;
	}

	public NavigationBar setTitle(String title) {
		titleText.setText(title);
		return this;
	}

	public NavigationBar setTitleColor(int titleColor) {
		titleText.setTextColor(titleColor);
		return this;
	}

	public NavigationBar showRightText() {
		rightText.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar setRightTextColor(int color) {
		rightText.setTextColor(color);
		return this;
	}

	public NavigationBar hideRightText() {
		rightText.setVisibility(GONE);
		return this;
	}

	public NavigationBar setRightText(String text) {
		rightText.setText(text);
		return this;
	}

	public TextView getRightText() {
		rightText.setVisibility(VISIBLE);
		return rightText;
	}

	public NavigationBar setRightTextClickListener(OnClickListener listener) {
		rightText.setOnClickListener(listener);
		return this;
	}

	public NavigationBar showRightIcon() {
		rightIcon.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar showRightIcon2() {
		rightIcon2.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar showRightIcon3() {
		rightIcon3.setVisibility(VISIBLE);
		return this;
	}

	public NavigationBar hideRightIcon3() {
		rightIcon3.setVisibility(GONE);
		return this;
	}

	public NavigationBar hideRightIcon() {
		rightIcon.setVisibility(GONE);
		return this;
	}

	public NavigationBar hideRightIcon2() {
		rightIcon2.setVisibility(GONE);
		return this;
	}

	public ImageView getRightIcon() {
		return rightIcon;
	}

	public ImageView getRightIcon2() {
		return rightIcon2;
	}

	public ImageView getRightIcon3() {
		return rightIcon3;
	}

	public NavigationBar setRightIconResource(int resId) {
		rightIcon.setImageResource(resId);
		return this;
	}

	public NavigationBar setRightIcon2Resource(int resId) {
		rightIcon2.setImageResource(resId);
		return this;
	}

	public NavigationBar setRightIcon3Resource(int resId) {
		rightIcon3.setImageResource(resId);
		return this;
	}

	public NavigationBar setRightIconClickListener(OnClickListener listener) {
		rightIcon.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon2ClickListener(OnClickListener listener) {
		rightIcon2.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon3ClickListener(OnClickListener listener) {
		rightIcon3.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIconResAndEvent(int resId, OnClickListener listener) {
		rightIcon.setVisibility(VISIBLE);
		rightIcon.setImageResource(resId);
		rightIcon.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon2ResAndEvent(int resId, OnClickListener listener) {
		rightIcon2.setVisibility(VISIBLE);
		rightIcon2.setImageResource(resId);
		rightIcon2.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightIcon3ResAndEvent(int resId, OnClickListener listener) {
		rightIcon3.setVisibility(VISIBLE);
		rightIcon3.setImageResource(resId);
		rightIcon3.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setLeftTextAndEvent(String text, OnClickListener listener) {
		leftText.setVisibility(VISIBLE);
		leftText.setText(text);
		leftText.setOnClickListener(listener);
		return this;
	}

	public NavigationBar setRightTextAndEvent(String text, OnClickListener listener) {
		rightText.setVisibility(VISIBLE);
		rightText.setText(text);
		rightText.setOnClickListener(listener);
		return this;
	}

	public void setBackgroundAlpha(float alpha) {
		if (alpha < 1) {
			bottomLine.setVisibility(GONE);
			rootLayout.setBackgroundColor(Color.argb((int)(alpha * 255), 252, 252, 252));
		} else {
			bottomLine.setVisibility(VISIBLE);
			//rootLayout.setBackgroundColor(getResources().getColor(R.color.bg));
		}
	}
}
