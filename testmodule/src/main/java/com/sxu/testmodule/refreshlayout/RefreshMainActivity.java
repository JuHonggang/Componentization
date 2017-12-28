package com.sxu.testmodule.refreshlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sxu.testmodule.R;
import com.sxu.testmodule.refreshlayout.test.RefreshMainFragment;

/**
 * @author Freeman
 * @date 2017/12/28
 */


public class RefreshMainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh_main_layout);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.container_layout, new RefreshMainFragment());
		transaction.commit();
	}
}
