package com.gandsoft.openguide.activity.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRecycleviewAdapter extends RecyclerView.Adapter<ScheduleRecycleviewAdapter.MyViewHolder> {
    private List<EventScheduleListDateDataList> models;
    private Context context;


    public ScheduleRecycleviewAdapter(Context context, ArrayList<EventScheduleListDateDataList> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_schedule_adapter, parent, false);
        return new ScheduleRecycleviewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventScheduleListDateDataList model = models.get(position);
        holder.tvRvScheduleTimefvbi.setText(model.getWaktu());
        holder.tvRvScheduleTitlefvbi.setText(model.getSchedule_title());
        if (!TextUtils.isEmpty(model.getLocation())) {
            Log.d("Lihat", "onBindViewHolder ScheduleRecycleviewAdapter : " + model.getLocation());
            holder.llRvScheduleLocationfvbi.setVisibility(View.VISIBLE);
            holder.tvRvScheduleLocationTitlefvbi.setText(model.getLocation());
        }else {
            holder.llRvScheduleLocationfvbi.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getDetail())) {
            Log.d("Lihat", "onBindViewHolder ScheduleRecycleviewAdapter : " + model.getDetail());
            holder.bRvScheduleDetailfvbi.setVisibility(View.VISIBLE);
            holder.bRvScheduleDetailfvbi.setText(model.getAction());
            holder.bRvScheduleDetailfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            holder.tvRvScheduleDetailfvbi.setText(model.getDetail());
            holder.tvRvScheduleDetailfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.bRvScheduleDetailfvbi.setVisibility(View.VISIBLE);
                    holder.tvRvScheduleDetailfvbi.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LinearLayout llRvScheduleLocationfvbi;
        private final TextView tvRvScheduleTimefvbi, tvRvScheduleTitlefvbi, tvRvScheduleLocationTitlefvbi, tvRvScheduleDetailfvbi;
        private final Button bRvScheduleDetailfvbi, bRvScheduleLiveQAfvbi;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);

            tvRvScheduleTimefvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleTime);
            tvRvScheduleTitlefvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleTitle);
            llRvScheduleLocationfvbi = (LinearLayout) itemView.findViewById(R.id.llRvScheduleLocation);
            tvRvScheduleLocationTitlefvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleLocationTitle);
            bRvScheduleDetailfvbi = (Button) itemView.findViewById(R.id.bRvScheduleDetail);
            tvRvScheduleDetailfvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleDetail);
            bRvScheduleLiveQAfvbi = (Button) itemView.findViewById(R.id.bRvScheduleLiveQA);

        }

        @Override
        public void onClick(View v) {

        }

    }

    public void removeAt(int position) {
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, models.size());
    }

    public void setData(List<EventScheduleListDateDataList> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<EventScheduleListDateDataList> datas) {
        models.clear();
        models = datas;
        notifyDataSetChanged();

    }

    public void addDatas(List<EventScheduleListDateDataList> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    /*public void setReplaceData(List<EventScheduleListDateDataList> datas) {
        if (models.size() > 0) {
            models.clear();
            models = datas;
            notifyDataSetChanged();
        } else {
            models.addAll(datas);
            notifyItemRangeInserted(models.size(), datas.size());
        }
    }*/

    /*public void setListContent(ArrayList<TaskRecViewPojo> list_members) {
        this.list_members = list_members;
        notifyItemRangeChanged(0, list_members.size());
    }*/

}
