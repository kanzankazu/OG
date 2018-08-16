package com.gandsoft.openguide.activity.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

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
            time = (TextView) itemView.findViewById(R.id.tvRvScheduleTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_home_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PostRecViewPojo model = models.get(position);
        holder.user_name.setText(model.getName());
        holder.content.setText(model.getContent());
        holder.time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<PostRecViewPojo> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<PostRecViewPojo> datas){
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<PostRecViewPojo> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(),datas.size());
    }

    /*public void setListContent(ArrayList<PostRecViewPojo> list_members) {
        this.models = list_members;
        notifyItemRangeChanged(0, list_members.size());
    }*/

}
