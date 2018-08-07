package com.gandsoft.openguide.activity.infomenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gandsoft.openguide.activity.gallery.Global;
import com.gandsoft.openguide.activity.gallery.zoomtransition.PicViewActivity;
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
            ImageView imageView = null;
            if (convertView == null) {
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int width = metrics.widthPixels / 9 * 3;
                int height = metrics.heightPixels / 16 * 3;

                final DisplayImageOptions thumbOptions = new DisplayImageOptions.Builder().cacheInMemory(true).build();
                final ImageLoader imageLoader = Global.getImageLoader(getApplicationContext());

                for (int i = 0; i < Global.getTestImagesCount(); i++) {
                    final int fi = i;
                    imageView= new ImageView (context);
                    imageView.setLayoutParams(new GridView.LayoutParams(width, height));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    final ImageViewAware thumbAware = new ImageViewAware(imageView);
                    final String url = Global.getTestImage(i).getThumb(100, 100).url;
                    imageLoader.displayImage(url, thumbAware, thumbOptions);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(bGalleryActivity.this, PicViewActivity.class);
                            intent.putExtra("image", Global.getTestImage(fi));
                            ImageSize targetSize = new ImageSize(thumbAware.getWidth(), thumbAware.getHeight());
                            String memoryCacheKey = MemoryCacheUtils.generateKey(url, targetSize);
                            intent.putExtra("cache_key", memoryCacheKey);
                            Rect rect = new Rect();
                            intent.putExtra("rect", rect);
                            startActivity(intent);
                        }
                    });
                }
            } else {
                imageView = (ImageView) convertView;
            }
            return imageView;
        }
    }
}
