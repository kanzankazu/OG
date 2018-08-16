package com.gandsoft.openguide.activity.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class TaskRecViewAdapter extends RecyclerView.Adapter<TaskRecViewAdapter.MyViewHolder> {
    private ArrayList<TaskRecViewPojo> list_members = new ArrayList<>();
    private List<EventScheduleListDateDataList> models;
    private Context context;


    public TaskRecViewAdapter(Context context, ArrayList<EventScheduleListDateDataList> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_schedule_adapter, parent, false);
        return new TaskRecViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventScheduleListDateDataList model = models.get(position);
        holder.tvRvScheduleTimefvbi.setText(model.getDate());
        holder.tvRvScheduleTimefvbi.setText(model.getSchedule_title());
        if (!model.getLocation().isEmpty() || model.getLocation() != null) {
            holder.llRvScheduleLocationfvbi.setVisibility(View.VISIBLE);
            holder.tvRvScheduleTimefvbi.setText(model.getLocation());
        }
        if (!model.getDetail().isEmpty() || model.getDetail() != null) {
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
        return list_members.size();
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
        list_members.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, list_members.size());
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

    public void setListContent(ArrayList<TaskRecViewPojo> list_members) {
        this.list_members = list_members;
        notifyItemRangeChanged(0, list_members.size());
    }

}
