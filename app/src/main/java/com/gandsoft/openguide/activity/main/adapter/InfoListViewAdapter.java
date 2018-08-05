package com.gandsoft.openguide.activity.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.DeviceDetailUtil;

import java.util.ArrayList;
import java.util.List;

public class InfoListViewAdapter extends RecyclerView.Adapter<InfoListViewAdapter.ViewHolder> {

    private Context context;
    private List<InfoListviewModel> models = new ArrayList<>();

    public InfoListViewAdapter() {

    }

    public InfoListViewAdapter(Context context, List<InfoListviewModel> items) {
        this.context = context;
        models = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //LinearLayout llListInfofvbi;
        ImageView ivListInfofvbi;
        TextView tvListInfofvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            //llListInfofvbi = (LinearLayout) itemView.findViewById(R.id.llListInfo);
            ivListInfofvbi = (ImageView) itemView.findViewById(R.id.ivListInfo);
            tvListInfofvbi = (TextView) itemView.findViewById(R.id.tvListInfo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_info, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InfoListviewModel model = models.get(position);
        if (DeviceDetailUtil.isKitkatBelow()) {
            holder.ivListInfofvbi.setImageResource(model.getPicTitle());
        } else {
            holder.ivListInfofvbi.setImageDrawable(context.getDrawable(model.getPicTitle()));
        }
        holder.tvListInfofvbi.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<InfoListviewModel> datas) {
        models = datas;
    }

    public void replaceData(List<InfoListviewModel> datas) {
        models.clear();
        models.addAll(datas);
    }


    public void addDatas(List<InfoListviewModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
