package com.gandsoft.openguide.activity.infomenu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gandsoft.openguide.R;

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
        private final int length = 16;
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
                int width = metrics.widthPixels / 9*3;
                int height = metrics.heightPixels / 16*3;
                imageView.setLayoutParams(new GridView.LayoutParams(width, height));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(R.drawable.gallery);
            return imageView;
        }
    }
}
