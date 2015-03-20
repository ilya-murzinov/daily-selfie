package com.github.ilyamurzinov.dailyselfie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.ilyamurzinov.dailyselfie.DailySelfieApplication;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DailySelfieApplication.inject(this);
    }
}
