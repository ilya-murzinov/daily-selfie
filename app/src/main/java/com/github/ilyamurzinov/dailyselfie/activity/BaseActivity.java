package com.github.ilyamurzinov.dailyselfie.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.github.ilyamurzinov.dailyselfie.DailySelfieApplication;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class BaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DailySelfieApplication.inject(this);
    }
}
