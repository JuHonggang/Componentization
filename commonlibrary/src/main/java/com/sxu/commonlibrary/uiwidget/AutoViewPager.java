package com.sxu.commonlibrary.uiwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.sxu.commonlibrary.R;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/*******************************************************************************
 * FileName: cl_AutoViewPager
 * <p/>
 * Description:
 * <p/>
 * Author: juhg
 * <p/>
 * Version: v1.0
 * <p/>
 * Date: 16/8/1
 * <p/>
 * Copyright: all rights reserved by zhinanmao.
 *******************************************************************************/
public class AutoViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer, Runnable {

    private int mDefaultPager;
    private int mItemCount;
    private int mIndicatorSize;
    private int mIndicatorLayoutMargin;
    private int mIndicatorGap;
    private int mAutoSwitchTime;
    private int mIndicatorNormalIcon;
    private int mIndicatorSelectedIcon;
    private float mAlpha;
    private float mScale;
    private boolean mIsAutoPlay;
    private boolean mLoopPlay;
    private boolean mUserAnimation;
    private boolean mShowIndicator;

    private LinearLayout indicatorLayout;
    private LinearLayout containerLayout;
    private ViewPager viewPager;
    private Context context;
    private ImageView[] indicatorIcons;
    private OnItemViewListener listener;
    private Handler handler = new Handler();

    public AutoViewPager(Context context) {
        this(context, null);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoViewPager(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.cl_AutoViewPager);
        mDefaultPager = array.getInteger(R.styleable.cl_AutoViewPager_cl_defaultPager, 0);
        mItemCount = array.getInteger(R.styleable.cl_AutoViewPager_cl_itemCount, 0);
        mIndicatorSize = (int)array.getDimension(R.styleable.cl_AutoViewPager_cl_indicatorIconSize, 0);
        mIndicatorLayoutMargin = (int) array.getDimension(R.styleable.cl_AutoViewPager_cl_indicatorLayoutMargin, 10);
        mIndicatorGap = (int) array.getDimension(R.styleable.cl_AutoViewPager_cl_indicatorGap, 10);
        mIndicatorNormalIcon = array.getResourceId(R.styleable.cl_AutoViewPager_cl_indicatorNormalIcon, 0);
        mIndicatorSelectedIcon = array.getResourceId(R.styleable.cl_AutoViewPager_cl_indicatorSelectedIcon, 0);
        mAutoSwitchTime = array.getInteger(R.styleable.cl_AutoViewPager_cl_autoSwitchTime, 1000);
        mIsAutoPlay = array.getBoolean(R.styleable.cl_AutoViewPager_cl_isAutoPlay, false);
        mLoopPlay = array.getBoolean(R.styleable.cl_AutoViewPager_cl_loopPlay, false);
        mUserAnimation = array.getBoolean(R.styleable.cl_AutoViewPager_cl_userAnimation, false);
        mShowIndicator = array.getBoolean(R.styleable.cl_AutoViewPager_cl_showIndicator, false);
        mScale = array.getFloat(R.styleable.cl_AutoViewPager_cl_scale, 0.8f);
        mAlpha = array.getFloat(R.styleable.cl_AutoViewPager_cl_image_alpha, 0.8f);
        array.recycle();
        init();
    }

    private void init() {
        viewPager = new ViewPager(context);
        viewPager.setId(generateId());
        viewPager.setClipChildren(false);
        viewPager.addOnPageChangeListener(this);
        if (mUserAnimation) {
            viewPager.setPageTransformer(true, this);
            containerLayout = new LinearLayout(context);
            containerLayout.setId(generateId());
            containerLayout.setGravity(Gravity.CENTER);
            containerLayout.setClipChildren(false);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            containerLayout.setLayoutParams(params);
            containerLayout.addView(viewPager);
            containerLayout.setClipChildren(false);
            addView(containerLayout);

            containerLayout.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return viewPager.dispatchTouchEvent(event);
                }
            });
        } else {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(CENTER_IN_PARENT);
            viewPager.setLayoutParams(params);
            addView(viewPager);
            setClipChildren(false);
        }
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem() % mItemCount;
    }

    public void setCurrentItem(int index) {
        mDefaultPager = index;
    }

    public void setIndicatorIconSize(int size) {
        this.mIndicatorSize = size;
    }

    public void setIndicatorLayoutMargin(int margin) {
        this.mIndicatorLayoutMargin = margin;
    }

    public void setIndicatorGap(int gap) {
        this.mIndicatorGap = gap;
    }

    public void setmIndicatorNormalIcon(int resId) {
        mIndicatorNormalIcon = resId;
    }

    public void setmIndicatorSelectedIcon(int resId) {
        mIndicatorNormalIcon = resId;
    }

    public void setSwitchTime(int switchTime) {
        this.mAutoSwitchTime = switchTime;
    }

    public void setAutoPlay(boolean isAutoPlay) {
        this.mIsAutoPlay = isAutoPlay;
    }

    public void setLoopPlay(boolean isLoopPlay) {
        this.mLoopPlay = isLoopPlay;
    }

    public void setUseAnimation(boolean use) {
        this.mUserAnimation = use;
    }

    public void setShowIndicator(boolean showIndicator) {
        this.mShowIndicator = showIndicator;
    }

    public void setPagerAnimation(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
    }

    @Override
    public void run() {
        if (mIsAutoPlay) {
            int index = viewPager.getCurrentItem();
            index++;
            viewPager.setCurrentItem(index);
        }
    }

    private void addIndicatorLayout() {
        indicatorLayout = new LinearLayout(context);
        indicatorLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        indicatorIcons = new ImageView[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            indicatorIcons[i] = new ImageView(context);
            LinearLayout.LayoutParams params;
            if (mIndicatorSize > 0) {
                params = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
            } else {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.leftMargin = mIndicatorGap;
            indicatorIcons[i].setLayoutParams(params);
            if (mDefaultPager == i) {
                indicatorIcons[i].setImageResource(mIndicatorSelectedIcon);
            } else {
                indicatorIcons[i].setImageResource(mIndicatorNormalIcon);
            }
            indicatorLayout.addView(indicatorIcons[i]);
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int gravity = Gravity.BOTTOM;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            gravity = getGravity();
        }
        int anchorId = (containerLayout != null && containerLayout.getId() != -1) ? containerLayout.getId() : viewPager.getId();
        if (gravity == Gravity.TOP) {
            params.topMargin = mIndicatorLayoutMargin;
            params.addRule(ALIGN_TOP, anchorId);
        } else {
            params.bottomMargin = mIndicatorLayoutMargin;
            params.addRule(ALIGN_BOTTOM, anchorId);
        }

        indicatorLayout.setLayoutParams(params);
        addView(indicatorLayout);
    }

    public void setAdapter(int count) {
        if (count > 0) {
            this.mItemCount = count;
            viewPager.setAdapter(new MyPagerAdapter());
            viewPager.setOffscreenPageLimit(mItemCount);
            viewPager.setCurrentItem(mDefaultPager);
            if (mShowIndicator) {
                addIndicatorLayout();
            }

            if (mIsAutoPlay) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = viewPager.getCurrentItem();
                        index++;
                        viewPager.setCurrentItem(index);
                    }
                }, mAutoSwitchTime);
            }
        }
    }

    /**
     * generge id for new created veiw.
     *
     * @return
     */
    private int generateId() {
        for (;;) {
            AtomicInteger sNextGeneratedId = new AtomicInteger(1);
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    private void resetIndicatorIcon(int position) {
        for (int i = 0; i < mItemCount; i++) {
            if (position == i) {
                indicatorIcons[i].setImageResource(mIndicatorSelectedIcon);
            } else {
                indicatorIcons[i].setImageResource(mIndicatorNormalIcon);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        if (mShowIndicator) {
            resetIndicatorIcon(position % mItemCount);
        }
        if (mIsAutoPlay || mLoopPlay) {
            handler.removeCallbacks(this);
            handler.postDelayed(this, mAutoSwitchTime);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 实现ViewPager的动画效果
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        if (position >= -1 && position <= 0) { // 当前页面滑出屏幕(0-->-1)
            float scaleRatio = (1 - mScale) * Math.abs(position);
            page.setScaleX(1 - scaleRatio);
            page.setScaleY(1 - scaleRatio);
            float alpha = (1 - mAlpha) * Math.abs(position);
            page.setAlpha(1 - alpha);
        } else if (position > 0 && position <= 1) { // 新页面滑入(1-->0)
            float scaleRatio = (1 - mScale) * (1 - position);
            page.setScaleX(mScale + scaleRatio);
            page.setScaleY(mScale + scaleRatio);
            float alpha = (1 - mAlpha) * (1 - position);
            page.setAlpha(mAlpha + alpha);
        } else {
            /**
             * Nothing
             */
        }
    }

    public void setPageMargin(int margin) {
        viewPager.setPageMargin(margin);
    }

    /**
     * 设置切换过程的时间
     * @param duration
     */
    public void setDuration(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            MyScroller scroller = new MyScroller(viewPager.getContext(), new LinearInterpolator());
            field.set(viewPager, scroller);
            scroller.setDuration(duration);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void setOnItemViewListener(OnItemViewListener listener) {
        this.listener = listener;
    }

    public interface OnItemViewListener {
        View getView(ViewGroup container, int position);
    }

    public class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mIsAutoPlay || mLoopPlay) {
                return Integer.MAX_VALUE;
            } else {
                return mItemCount;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return listener.getView(container, position % mItemCount);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class MyScroller extends Scroller {

        private int mDuration;

        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context,  Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            this.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setDuration(int duration) {
            this.mDuration = duration;
        }
    }
}
