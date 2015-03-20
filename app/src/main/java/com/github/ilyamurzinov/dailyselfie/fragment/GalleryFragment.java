package com.github.ilyamurzinov.dailyselfie.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ilyamurzinov.dailyselfie.R;
import com.github.ilyamurzinov.dailyselfie.activity.MainActivity;
import com.github.ilyamurzinov.dailyselfie.component.BitmapHelper;
import com.github.ilyamurzinov.dailyselfie.component.ImagesDAO;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class GalleryFragment extends BaseFragment {

    private static final String FRAGMENT_ID = "selfie";
    private static final int ICON_DIMEN = 100;

    private List<File> images;
    private BaseAdapter adapter;

    @Inject
    public ImagesDAO imagesDAO;

    @Inject
    public BitmapHelper bitmapHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        images = imagesDAO.getImages();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.gallery);

        adapter = new GalleryAdapter(inflater);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelfieFragment selfieFragment = new SelfieFragment();
                Bundle bundle = new Bundle();
                bundle.putString(MainActivity.ARGUMENT_PATH, images.get(position).getAbsolutePath());
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
                final File image = images.get(position);

                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.confirm_selfie_deletion_dialog_title))
                        .setMessage(String.format(getString(R.string.confirm_selfie_deletion_dialog_message), image.getName()))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imagesDAO.delete(image);
                                images = imagesDAO.getImages();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
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

    public void update() {
        images = imagesDAO.getImages();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class GalleryAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        GalleryAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
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

            holder.textView.setText(images.get(position).getName());

            String path = images.get(position).getAbsolutePath();
            holder.imageView.setImageBitmap(
                    GalleryFragment.this.bitmapHelper
                            .decodeSampledBitmapFromResource(path, ICON_DIMEN, ICON_DIMEN)
            );

            return view;
        }
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
