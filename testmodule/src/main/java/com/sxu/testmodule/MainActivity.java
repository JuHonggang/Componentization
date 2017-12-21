package com.sxu.testmodule;

import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private int imageLoaderType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		View imageLoaderButton = findViewById(R.id.image_loader_button);
		View albumButton = findViewById(R.id.album_button);
		View chooseImageButton = findViewById(R.id.choose_image_button);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

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

//		TextView textView = (TextView) findViewById(R.id.text);
//		Button button = (Button) findViewById(R.id.button);
//		final ImageView imageView = findViewById(R.id.image);
//		ShadowDrawable drawable = new ShadowDrawable(getResources().getColor(R.color.cl_b2_40), 20);
//		button.setBackgroundDrawable(drawable);
//		textView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//startActivity(new Intent(TestActivity.this, KotlinTestActivity.class));
//				ChoosePhotoDialog dialog = new ChoosePhotoDialog(MainActivity.this, new String[] {"拍照", "从相册选择"});
//				dialog.show();
//				dialog.setOnItemListener(new AdapterView.OnItemClickListener() {
//					@Override
//					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//						if (i == 0) {
//							ChoosePhotoManager.getInstance().choosePhotoFromCamera(MainActivity.this);
//						} else {
//							ChoosePhotoManager.getInstance().choosePhotoFromAlbum(MainActivity.this);
//						}
//						//ChoosePhotoManager.getInstance().setAutoCrop(true);
//						ChoosePhotoManager.getInstance().setChoosePhotoListener(new OnChoosePhotoListener() {
//							@Override
//							public void choosePhotoFromAlbum(Uri uri, String errMsg) {
//								LogUtil.i("uri===" + uri != null ? uri.getPath() : errMsg);
//								imageView.setImageURI(uri);
//							}
//
//							@Override
//							public void choosePhotoFromCamera(Uri uri, String errMsg) {
//								LogUtil.i("uri===" + uri != null ? uri.getPath() : errMsg);
//								imageView.setImageURI(uri);
//							}
//
//							@Override
//							public void cropPhoto(Uri uri, String errMsg) {
//								LogUtil.i("uri===" + uri != null ? uri.getPath() : errMsg);
//								imageView.setImageURI(uri);
//							}
//						});
//					}
//				});
//			}
//		});
//
//
//		button.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				PhotoPicker photoPicker = new PhotoPicker.Builder()
//						.setMaxPhotoCount(4)
//						.builder();
//				photoPicker.chooseImage(MainActivity.this);
//			}
//		});
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PhotoPicker.REQUEST_CODE_CHOOSE_PHOTO && data != null) {
			ArrayList<String> selectedPhotos = data.getStringArrayListExtra(PhotoPicker.SELECTED_PHOTOS);
			if (selectedPhotos != null && selectedPhotos.size() > 0) {
				for (String photoPath : selectedPhotos) {
					LogUtil.i(photoPath + "\n");
				}
			}
		}
		//ChoosePhotoManager.getInstance().onActivityResult(this, requestCode, data);
	}
}
