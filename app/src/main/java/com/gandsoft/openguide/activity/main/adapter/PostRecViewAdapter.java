package com.gandsoft.openguide.activity.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class PostRecViewAdapter extends RecyclerView.Adapter<PostRecViewAdapter.ViewHolder> {

    private List<PostRecViewPojo> models = new ArrayList<>();

    public PostRecViewAdapter(List<PostRecViewPojo> items) {
        models = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, content, time;

        public ViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*view = inflater.inflate(R.layout.recview_post, parent, false);
        holder = new ViewHolder(view);
        return holder;*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PostRecViewPojo model = models.get(position);
        holder.user_name.setText(model.getName());
        holder.content.setText(model.getContent());
        holder.time.setText(model.getTime());
    }

    public void setData(List<PostRecViewPojo> datas) {
        models = datas;
    }

    public void replaceData(List<PostRecViewPojo> datas){
        models.clear();
        models.addAll(datas);
    }


    public void addDatas(List<PostRecViewPojo> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(),datas.size());
    }


    /*public void addDatas(List<PostRecViewPojo> datas) {
        models.addAll(datas);
    }*/

    public void setListContent(ArrayList<PostRecViewPojo> list_members) {
        this.models = list_members;
        notifyItemRangeChanged(0, list_members.size());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}
