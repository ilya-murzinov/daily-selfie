package com.github.ilyamurzinov.dailyselfie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ilyamurzinov.dailyselfie.component.BitmapHelper;
import com.github.ilyamurzinov.dailyselfie.R;
import com.github.ilyamurzinov.dailyselfie.activity.MainActivity;

import javax.inject.Inject;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class SelfieFragment extends BaseFragment {

    public static final int IMAGE_DIMEN = 1000;

    @Inject
    public BitmapHelper bitmapHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selfie, null);
        String path = (String) getArguments().get(MainActivity.ARGUMENT_PATH);
        ImageView imageView = (ImageView) view.findViewById(R.id.selfie_large);
        imageView.setImageBitmap(bitmapHelper.decodeSampledBitmapFromResource(path, IMAGE_DIMEN, IMAGE_DIMEN));
        return view;
    }
}