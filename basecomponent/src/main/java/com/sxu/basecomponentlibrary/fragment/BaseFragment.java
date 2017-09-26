package com.sxu.basecomponentlibrary.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by juhg on 16/2/22.
 */
public abstract class BaseFragment extends Fragment {

    public View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResId() != 0) {
            rootView = inflater.inflate(getLayoutResId(), null);
            getViews();
            initFragment();
        }

        return rootView;
    }

    public abstract int getLayoutResId();

    public abstract void getViews();

    public abstract void initFragment();
}
