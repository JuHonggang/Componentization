package com.sxu.smartpicture.album;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxu.smartpicture.utils.DisplayUtil;
import com.sxu.smartpicture.R;
import com.sxu.smartpicture.imageloader.ImageLoaderManager;
import com.sxu.smartpicture.imageloader.WrapImageView;

import java.util.ArrayList;

/**
 * Created by Freeman on 17/3/31.
 */

public class PhotoPreviewFragment extends Fragment {

	private ViewPager mViewPager;

	private ArrayList<String> allPhotoPath;
	private ChoosePhotoActivity.OnItemPhotoCheckedListener checkedListener;

	public static PhotoPreviewFragment getInstance(int currentIndex, ArrayList<String> allPhotoPath) {
		PhotoPreviewFragment fragment = new PhotoPreviewFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("currentIndex", currentIndex);
		bundle.putStringArrayList("allPhotoPath", allPhotoPath);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mViewPager = (ViewPager) inflater.inflate(R.layout.fragment_photo_preview_layout, null);
		return mViewPager;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		final ChoosePhotoActivity context = (ChoosePhotoActivity) getActivity();
		if (bundle != null) {
			allPhotoPath = bundle.getStringArrayList("allPhotoPath");
			if (allPhotoPath != null && allPhotoPath.size() > 0) {
				mViewPager.setAdapter(new PhotoAdapter());
				int currentIndex = bundle.getInt("currentIndex");
				mViewPager.setCurrentItem(currentIndex);
				context.setCheckIconStatus(allPhotoPath.get(currentIndex));
			}
		}

		if (getActivity() instanceof ChoosePhotoActivity) {
			((ChoosePhotoActivity) getActivity()).checkIcon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (checkedListener != null) {
						checkedListener.onItemChecked(context.checkIcon, !v.isSelected(),
								allPhotoPath.get(mViewPager.getCurrentItem()));
					}
				}
			});
		}

		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				context.setCheckIconStatus(allPhotoPath.get(position));
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	public void setOnItemPhotoCheckedListener(ChoosePhotoActivity.OnItemPhotoCheckedListener listener) {
		this.checkedListener = listener;
	}

	private class PhotoAdapter extends PagerAdapter {

		@Override
		public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
			return view.equals(object);
		}

		@Override
		public int getCount() {
			return allPhotoPath.size();
		}

		@NonNull
		@Override
		public Object instantiateItem(@NonNull ViewGroup container, int position) {
			WrapImageView imageView = new WrapImageView(getActivity());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				imageView.setTransitionName("Preview" + position);
			}
			ViewGroup.LayoutParams params = container.getLayoutParams();
			params.width = DisplayUtil.getScreenWidth();
			params.height = DisplayUtil.getScreenHeight();
			ImageLoaderManager.getInstance().displayImage(allPhotoPath.get(position), imageView);
			container.addView(imageView, params);

			return imageView;
		}

		@Override
		public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		}

	}
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//
//
//	}

	//    private TextView completeText;
//    private ViewPager photoPager;
//    private CommonNavigationBar navigationBar;
//
//    private static final String TAG_CURRENT_ITEM = "currentItem";
//    private static final String TAG_LOCATION = "location";
//    private static final String TAG_WIDTH = "width";
//    private static final String TAG_HEIGHT = "height";
//    private static final String TAG_SELECTED_PHOTOS = "selectedPhotos";
//    private static final String TAG_ALL_PHOTOS = "allPhotos";
//
//    private int width;
//    private int height;
//    private int[] location;
//    private int currentItem;
//    private int maxImageWidth;
//    private int maxImageHeight;
//    private int imageHeight;
//    private DisplayImageOptions imgOptions;
//    private ArrayList<String> allPhotos = new ArrayList<>();
//
//    public static PhotoPreviewFragment newInstance(int currentItem, int[] location, int width,
//                                                   int height, ArrayList<String> selectedPhotos, ArrayList<String> allPhotos) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(TAG_CURRENT_ITEM, currentItem);
//        bundle.putIntArray(TAG_LOCATION, location);
//        bundle.putInt(TAG_WIDTH, width);
//        bundle.putInt(TAG_HEIGHT, height);
//        bundle.putSerializable(TAG_SELECTED_PHOTOS, selectedPhotos);
//        bundle.putSerializable(TAG_ALL_PHOTOS, allPhotos);
//        PhotoPreviewFragment fragment = new PhotoPreviewFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.fragment_photo_preview_layout;
//    }
//
//    @Override
//    protected void getViews() {
//        photoPager = (ViewPager) mContentView.findViewById(R.id.view_pager);
//        completeText = ((ChoosePhotoActivity)getActivity()).getCompleteText();
//        navigationBar = ((ChoosePhotoActivity)getActivity()).getNavigationBar();
//    }
//
//    @Override
//    protected void initFragment() {
//        width = getArguments().getInt(TAG_WIDTH);
//        height = getArguments().getInt(TAG_HEIGHT);
//        location = getArguments().getIntArray(TAG_LOCATION);
//        currentItem = getArguments().getInt(TAG_CURRENT_ITEM, 0);
//        if (location == null) {
//            location = new int[] {0, 0};
//        }
//        maxImageWidth = mScrWidth;
//        maxImageHeight = AndroidPlatformUtil.getDeviceScreenHeight(getActivity())
//                         - AndroidPlatformUtil.getStatusHeight(getActivity())
//                         - AndroidPlatformUtil.dpToPx(94, getActivity());
//        imageHeight = (int) (mScrWidth * 2.0f / 3.0f);
//        initOptions();
//        allPhotos.addAll(getArguments().getStringArrayList(TAG_ALL_PHOTOS));
//        photoPager.setAdapter(new PreviewAdapter());
//        photoPager.setCurrentItem(currentItem);
//        //setAnimation(true);
//        photoPager.setVisibility(View.VISIBLE);
//        if (((ChoosePhotoActivity)getActivity()).selectedPhotos.contains(allPhotos.get(currentItem))) {
//            navigationBar.setRightIconResource(R.drawable.photo_selected_icon);
//        } else {
//            navigationBar.setRightIconResource(R.drawable.photo_unselected_icon);
//        }
//        navigationBar.setRightIconClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (((ChoosePhotoActivity)getActivity()).selectedPhotos
//                        .contains(allPhotos.get(currentItem))) {
//                    navigationBar.setRightIconResource(R.drawable.photo_unselected_icon);
//                } else {
//                    if (((ChoosePhotoActivity)getActivity()).selectedPhotos.size() < 4) {
//                        ((ChoosePhotoActivity)getActivity()).selectedPhotos.add(allPhotos.get(currentItem));
//                        navigationBar.setRightIconResource(R.drawable.photo_selected_icon);
//                        completeText.setText("完成(" + ((ChoosePhotoActivity)getActivity()).selectedPhotos.size() + ")");
//                    } else {
//                        ToastUtil.show(getActivity(), "你最多可选择4张照片");
//                    }
//                }
//            }
//        });
//
//        photoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                if (listener != null) {
////                    listener.onPageSelected(position);
////                }
//                currentItem = position;
//                if (((ChoosePhotoActivity)getActivity()).selectedPhotos.contains(allPhotos.get(currentItem))) {
//                    navigationBar.setRightIconResource(R.drawable.photo_selected_icon);
//                } else {
//                    navigationBar.setRightIconResource(R.drawable.photo_unselected_icon);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    private void setAnimation(boolean isEntry) {
//        final float imageWidth = AndroidPlatformUtil.getDeviceScreenWidth(getActivity());
//        final float imageHeight = imageWidth * 2.0f / 3;
//        ScaleAnimation animation = null;
//        if (isEntry) {
//            animation = new ScaleAnimation(width * 1.0f / imageWidth, 1.0f, height * 1.0f / imageHeight, 1.0f,
//                    location[0] + width / 2, location[1] + height / 2);
//        } else {
//            animation = new ScaleAnimation(1.0f, 0, 1.0f, 0, location[0] + width / 2, location[1] + height / 2);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    //rootLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    //getActivity().finish();
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//        }
//        animation.setDuration(400);
//        animation.setFillAfter(true);
//        photoPager.startAnimation(animation);
//    }
//
//    private void initOptions() {
//        imgOptions = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.default_placeholder_image)
//                .showImageOnFail(R.drawable.default_placeholder_image)
//                .resetViewBeforeLoading(true)
//                .cacheOnDisk(false)
//                .cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
//                .build();
//    }
//
//    public class PreviewAdapter extends PagerAdapter {
//        @Override
//        public int getCount() {
//            return allPhotos.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view.equals(object);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            PhotoView photoView = new PhotoView(getActivity());
//            LogUtil.i("path==" + allPhotos.get(position));
//            ImageLoader.getInstance().displayImage("file://" + allPhotos.get(position), new ImageViewAware(photoView), imgOptions,
//                    new ImageSize(mScrWidth, imageHeight), null, null);
//            container.addView(photoView);
//            return photoView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }
}
