package com.sxu.commonbusiness.launchguide.activity;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sxu.basecomponentlibrary.activity.BaseActivity;
import com.sxu.commonbusiness.R;
import com.sxu.commonbusiness.launchguide.bean.DeviceInfo;
import com.sxu.commonlibrary.commonutils.DisplayUtil;
import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.commonlibrary.commonutils.ResourceUtil;
import com.sxu.commonlibrary.commonutils.ToastUtil;
import com.sxu.commonlibrary.commonutils.ViewBgUtil;
import com.sxu.commonlibrary.datasource.http.bean.BaseProtocolBean;
import com.sxu.commonlibrary.datasource.http.impl.OkHttpQuery;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juhg on 15/9/30.
 */
public class GuideActivity extends BaseActivity {

    private View entryText;
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private boolean isFirst = true;
    private int[] guideImage = new int[] {
            R.drawable.cb_guide_image_1,
            R.drawable.cb_guide_image_2,
            R.drawable.cb_guide_image_3,
    };
    private View[] indicator = new View[guideImage.length];
    private final int IMAGE_HEIGHT = 2001;          // 设计图的高度
    private final int BUTTON_MARGIN_BOTTOM = 88;    // 最后一张设计图上进入按钮离底部的距离

    @Override
    protected int getLayoutResId() {
        return R.layout.cb_activity_guide_layout;
    }

    @Override
    protected void getViews() {
        entryText = findViewById(R.id.entry_text);
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicator_layout);
    }

    @Override
    protected void initActivity() {
        int contentHeight = DisplayUtil.getScreenHeight() - DisplayUtil.getStatusHeight(this);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) entryText.getLayoutParams();
        params.bottomMargin = BUTTON_MARGIN_BOTTOM * contentHeight / IMAGE_HEIGHT;
        entryText.setLayoutParams(params);
        loadIndicatorItems();
        selectCurrentDot(0);
        entryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitPhoneInfo();
            }
        });

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return guideImage.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(GuideActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(guideImage[position]);
                container.addView(imageView);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
                if(position == guideImage.length - 1)
                {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        commitPhoneInfo();
                    }
                } else {
                    isFirst = true;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == guideImage.length - 1) {
                    entryText.setVisibility(View.VISIBLE);
                } else {
                    entryText.setVisibility(View.GONE);
                }
                selectCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void loadIndicatorItems() {
        int size = DisplayUtil.dpToPx(6);
        int marginLeft = DisplayUtil.dpToPx(8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.leftMargin = marginLeft;
        int[] indicatorColors = {
                ResourceUtil.getColor(this, R.color.cl_t1),
                ResourceUtil.getColor(this, R.color.cl_t2)
        };
        for (int i = 0; i < guideImage.length; i++) {
            View indicatorView = new View(this);
            ViewBgUtil.setSelectorBg(indicatorView, android.R.attr.state_selected, GradientDrawable.OVAL,
                    indicatorColors, 0);
            indicatorLayout.addView(indicatorView, params);
        }
    }

    private void selectCurrentDot(int position) {
        int childCount = indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == position) {
                indicatorLayout.getChildAt(i).setSelected(true);
            } else {
                indicatorLayout.getChildAt(i).setSelected(false);
            }
        }
    }

    //用post方提交数据给法服务器
    private void commitPhoneInfo() {
        // http post commit
        OkHttpQuery<BaseProtocolBean> customQuery = new OkHttpQuery<BaseProtocolBean>(this, BaseProtocolBean.class, null);
        Map<String, String> reqMap = new HashMap<String, String>();
        DeviceInfo deviceInfo = new DeviceInfo(this);
        LogUtil.i("deviceInfo==" + deviceInfo.toString());
        ToastUtil.show(this, "进入主页");
    }
}