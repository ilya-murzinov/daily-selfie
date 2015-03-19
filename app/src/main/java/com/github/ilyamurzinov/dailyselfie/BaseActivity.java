package com.github.ilyamurzinov.dailyselfie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class BaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DailySelfieApplication) getApplication()).inject(this);
    }
}
