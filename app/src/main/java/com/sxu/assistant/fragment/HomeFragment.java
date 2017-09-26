package com.sxu.assistant.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxu.assistant.R;
import com.sxu.basecomponentlibrary.fragment.BaseProgressFragment;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/6/27
 */


public class HomeFragment extends BaseProgressFragment {

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_home_layout;
	}

	@Override
	public void requestData() {
		notifyLoadFinish(MSG_LOAD_FINISH);
	}

	@Override
	public void getViews() {
		View sameButton = rootView.findViewById(R.id.same_button);
		View differenceButton = rootView.findViewById(R.id.difference_button);

		sameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance()
						.build("/ab/a2")
						.navigation();
			}
		});

		differenceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARouter.getInstance().build("/testmodule/TestActivity").navigation();
			}
		});
	}

	@Override
	public void initFragment() {

	}
}

