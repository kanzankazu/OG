package com.gandsoft.openguide.activity.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Authuor: Hari vignesh Jayapalan
 * Created on: 6 Feb 2016
 * Email: hariutd@gmail.com
 * <p>
 * Implementing custom RecyclerView Adapter
 * Tutorial @ https://medium.com/@harivigneshjayapalan
 */
public class PostRecViewAdapter extends RecyclerView.Adapter<PostRecViewAdapter.MyViewHolder> {

    private List<PostRecViewPojo> mValues;
    private final LayoutInflater inflater;
    View view;
    MyViewHolder holder;
    private Context context;

    public PostRecViewAdapter(Context context, List<PostRecViewPojo> items) {
        this.context = context;
        mValues = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recview_post, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PostRecViewPojo recViewPojo = mValues.get(position);
        holder.user_name.setText(recViewPojo.getName());
        holder.content.setText(recViewPojo.getContent());
        holder.time.setText(recViewPojo.getTime());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, content, time;

        public MyViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);

        }

    }

    public void setListContent(ArrayList<PostRecViewPojo> list_members) {
        this.mValues = list_members;
        notifyItemRangeChanged(0, list_members.size());
    }

    public void setData(List<PostRecViewPojo> datas) {
        mValues = datas;
    }

    public void addDatas(List<PostRecViewPojo> datas) {
        mValues.addAll(datas);
    }

}
