package com.github.ilyamurzinov.dailyselfie;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DailySelfieApplication) getActivity().getApplication()).inject(this);
    }
}
