package com.gandsoft.openguide.activity.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import java.util.List;

public class InfoListViewAdapter extends ArrayAdapter<InfoListviewModel> {

    private final Context context;
    private final int resource;
    private final List<InfoListviewModel> objects;

    public InfoListViewAdapter(Context context, int resource, List<InfoListviewModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        ImageView ivListInfofvbi = (ImageView) view.findViewById(R.id.ivListInfo);
        TextView tvListInfofvbi = (TextView) view.findViewById(R.id.tvListInfo);

        InfoListviewModel model = objects.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivListInfofvbi.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
        } else {
            ivListInfofvbi.getBackground().setColorFilter(Color.parseColor("#606b8b"), PorterDuff.Mode.SRC_ATOP);
        }
        ivListInfofvbi.setImageDrawable(context.getResources().getDrawable(model.getPicTitle()));
        tvListInfofvbi.setText(model.getTitle());

        return view;
    }
}
