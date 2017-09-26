package com.sxu.commonlibrary.commonutils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * View动画类
 *
 * @author Freeman
 * @date 17/6/20
 */


public class ViewAnimationUtil {

	private final static int DEFAULT_DURATION = 350;

	public void translate(int startX, int startY, int endX, int endY) {
		translate(startX, startY, endX, endY, DEFAULT_DURATION, new LinearInterpolator());
	}

	public void translate(int startX, int startY, int endX, int endY, int duration) {
		translate(startX, startY, endX, endY, duration, new LinearInterpolator());
	}

	public void translate(int startX, int startY, int endX, int endY, Interpolator interpolator) {
		translate(startX, startY, endX, endY, DEFAULT_DURATION, interpolator);
	}

	public void translate(int startX, int startY, int endX, int endY, int duration, Interpolator interpolator) {
		TranslateAnimation animation = new TranslateAnimation(startX, startY, endX, endY);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		animation.start();
	}

	public void scale(int fromX, int toX, int fromY, int toY) {
		translate(fromX, toX, fromY, toY, DEFAULT_DURATION, new LinearInterpolator());
	}

	public void scale(int fromX, int toX, int fromY, int toY, int duration) {
		translate(fromX, toX, fromY, toY, duration, new LinearInterpolator());
	}

	public void scale(int fromX, int toX, int fromY, int toY, Interpolator interpolator) {
		translate(fromX, toX, fromY, toY, DEFAULT_DURATION, interpolator);
	}

	public void scale(int fromX, int toX, int fromY, int toY, int duration, Interpolator interpolator) {
		ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		animation.start();
	}

	public void alpha(float fromAlpha, float toAlpha) {
		alpha(fromAlpha, toAlpha, DEFAULT_DURATION, new LinearInterpolator());
	}

	public void alpha(float fromAlpha, float toAlpha, int duration) {
		alpha(fromAlpha, toAlpha, duration, new LinearInterpolator());
	}

	public void alpha(float fromAlpha, float toAlpha, Interpolator interpolator) {
		alpha(fromAlpha, toAlpha, DEFAULT_DURATION, interpolator);
	}

	public void alpha(float fromAlpha, float toAlpha, int duration, Interpolator interpolator) {
		AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		animation.start();
	}

	public void rotate(float fromDegree, float toDegree) {
		rotate(fromDegree, toDegree, DEFAULT_DURATION, new LinearInterpolator());
	}

	public void rotate(float fromDegree, float toDegree, int duration) {
		rotate(fromDegree, toDegree, duration, new LinearInterpolator());
	}

	public void rotate(float fromDegree, float toDegree, Interpolator interpolator) {
		rotate(fromDegree, toDegree, DEFAULT_DURATION, interpolator);
	}

	public void rotate(float fromDegree, float toDegree, int duration, Interpolator interpolator) {
		RotateAnimation animation = new RotateAnimation(fromDegree, toDegree);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setInterpolator(interpolator);
		animation.start();
	}
}
