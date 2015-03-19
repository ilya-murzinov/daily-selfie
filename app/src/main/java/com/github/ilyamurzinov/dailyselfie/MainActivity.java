package com.github.ilyamurzinov.dailyselfie;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;


public class MainActivity extends BaseActivity {

    public static final String ARGUMENT_PATH = "path";

    private GalleryFragment galleryFragment;

    @Inject
    public AlarmHelper alarmHelper;

    @Inject
    public SelfieTaker selfieTaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            galleryFragment = new GalleryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, galleryFragment)
                    .commit();
        }

        alarmHelper.setAlarm();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_camera) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    selfieTaker.takeSelfie(MainActivity.this);
                    galleryFragment.update();
                    return true;
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        galleryFragment.update();
    }
}
