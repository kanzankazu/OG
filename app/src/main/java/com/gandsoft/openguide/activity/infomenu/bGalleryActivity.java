package com.gandsoft.openguide.activity.infomenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.gallery.Global;
import com.gandsoft.openguide.gallery.images.ImageSource;
import com.gandsoft.openguide.gallery.withviewpager.PagerActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

public class bGalleryActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridView gridView = new GridView(this);
        gridView.setNumColumns(3);
        setContentView(gridView);
        gridView.setAdapter(new GridViewAdapter(this));
    }

    private class GridViewAdapter extends BaseAdapter {
        private Context context;
        private final int length = Global.getTestImagesCount();

        public GridViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int width = metrics.widthPixels / 9 * 3;
                int height = metrics.heightPixels / 16 * 3;
                imageView.setLayoutParams(new GridView.LayoutParams(width, height));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                final DisplayImageOptions thumbOptions = new DisplayImageOptions.Builder().cacheInMemory(true).build();
                final ImageLoader imageLoader = Global.getImageLoader(getApplicationContext());


                for (int i = 0; i < Global.getTestImagesCount(); i++) {
                    final int fi = i;
                    imageView.setBackground(Drawable.createFromPath(Global.getTestImage(i).getThumb(100, 100).url));
                    final String url = Global.getTestImage(i).getThumb(100, 100).url;
                    imageLoader.displayImage(url, imageView, thumbOptions);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(bGalleryActivity.this, PagerActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            } else {gi
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(R.drawable.gallery);
            return imageView;
        }
    }
}
