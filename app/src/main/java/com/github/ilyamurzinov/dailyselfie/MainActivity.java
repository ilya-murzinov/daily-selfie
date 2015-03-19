package com.github.ilyamurzinov.dailyselfie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final String FOLDER_NAME = "selfies";
    public static final String ARGUMENT_PATH = "path";
    public static final String FILE_NAME = "file:///%s/%d%02d%02d_%02d%02d%02d.jpg";

    private static List<File> images;
    private static File imagesDir;
    private GalleryFragment galleryFragment;

    private AlarmHelper alarmHelper;

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

        imagesDir = new File(
                Environment.getExternalStorageDirectory() +
                        File.separator +
                        FOLDER_NAME
        );

        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }

        refreshImages();

        alarmHelper = new AlarmHelper(this);
        alarmHelper.setAlarm(1);
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
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Calendar calendar = Calendar.getInstance();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(String.format(
                            FILE_NAME,
                            MainActivity.imagesDir.getAbsolutePath(),
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DATE),
                            calendar.get(Calendar.HOUR),
                            calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND)
                    )));
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    return true;
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshImages();
        galleryFragment.adapter.notifyDataSetChanged();
    }

    private static void refreshImages() {
        images = getListFiles(imagesDir);
    }

    public static class GalleryFragment extends Fragment {

        private static final String FRAGMENT_ID = "selfie";
        private static final int ICON_DIMEN = 100;
        private ListView listView;
        private BaseAdapter adapter;

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            listView = (ListView) rootView.findViewById(R.id.gallery);

            adapter = new GalleryAdapter(inflater);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SelfieFragment selfieFragment = new SelfieFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARGUMENT_PATH, MainActivity.images.get(position).getAbsolutePath());
                    selfieFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, selfieFragment)
                            .addToBackStack(FRAGMENT_ID)
                            .commit();
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final File image = MainActivity.images.get(position);

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Are you sure?")
                            .setMessage(String.format("Do you want to delete selfie %s?", image.getName()))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    image.delete();
                                    refreshImages();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                    return true;
                }
            });

            return rootView;
        }

        static class GalleryAdapter extends BaseAdapter {
            private LayoutInflater inflater;

            GalleryAdapter(LayoutInflater inflater) {
                this.inflater = inflater;
            }

            @Override
            public int getCount() {
                return MainActivity.images.size();
            }

            @Override
            public Object getItem(int position) {
                return MainActivity.images.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                View view = convertView;

                if (view == null) {
                    view = inflater.inflate(R.layout.gallery_item, null);
                    holder = new ViewHolder();
                    holder.textView = (TextView) view.findViewById(R.id.text);
                    holder.imageView = (ImageView) view.findViewById(R.id.image);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

                holder.textView.setText(MainActivity.images.get(position).getName());

                String path = MainActivity.images.get(position).getAbsolutePath();
                holder.imageView.setImageBitmap(BitmapHelper.decodeSampledBitmapFromResource(path, ICON_DIMEN, ICON_DIMEN));

                return view;
            }
        }

        static class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }

    public static class SelfieFragment extends Fragment {

        public static final int IMAGE_DIMEN = 1000;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_selfie, null);
            String path = (String) getArguments().get(ARGUMENT_PATH);
            ImageView imageView = (ImageView) view.findViewById(R.id.selfie_large);
            imageView.setImageBitmap(BitmapHelper.decodeSampledBitmapFromResource(path, IMAGE_DIMEN, IMAGE_DIMEN));
            return view;
        }
    }

    private static List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListFiles(file));
                } else {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
