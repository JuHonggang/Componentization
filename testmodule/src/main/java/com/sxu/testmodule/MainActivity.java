package com.sxu.testmodule;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sxu.commonlibrary.commonutils.LogUtil;
import com.sxu.smartpicture.choosePicture.OnChoosePhotoListener;
import com.sxu.smartpicture.choosePicture.ChoosePhotoManager;
import com.sxu.smartpicture.choosePicture.ChoosePhotoDialog;
import com.sxu.smartpicture.album.PhotoPicker;
import com.sxu.smartpicture.imageloader.ImageLoaderManager;
import com.sxu.testmodule.refreshlayout.RefreshMainActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private int imageLoaderType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		LogUtil.i("========onCreate");
		View imageLoaderButton = findViewById(R.id.image_loader_button);
		View albumButton = findViewById(R.id.album_button);
		View chooseImageButton = findViewById(R.id.choose_image_button);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		View shadowView = findViewById(R.id.shadow_view);

		//shadowView.setBackground(new ShadowDrawable(Color.RED, 10));

		imageLoaderButton.setOnClickListener(this);
		albumButton.setOnClickListener(this);
		chooseImageButton.setOnClickListener(this);

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (group.getCheckedRadioButtonId()) {
					case R.id.fresco_radio:
						imageLoaderType = 0;
						break;
					case R.id.url_radio:
						imageLoaderType = 1;
						break;
					case R.id.glide_radio:
						imageLoaderType = 2;
						break;
				}
			}
		});

		shadowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, RefreshMainActivity.class));
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		LogUtil.i("========onNewIntent");
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogUtil.i("========onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		LogUtil.i("========onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.i("========onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.i("========onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtil.i("========onPause");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.image_loader_button:
				Intent intent = new Intent(this, ImageLoaderActivity.class);
				intent.putExtra("type", imageLoaderType);
				startActivity(intent);
				break;
			case R.id.album_button:
				startActivity(new Intent(this, AlbumActivity.class));
				break;
			case R.id.choose_image_button:
				startActivity(new Intent(this, ChooseAndCropImageActivity.class));
				break;
			default:
				break;
		}
	}
}
