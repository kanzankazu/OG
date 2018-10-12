package com.gandsoft.openguide.activity.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRecycleviewAdapter extends RecyclerView.Adapter<ScheduleRecycleviewAdapter.MyViewHolder> {
    private List<EventScheduleListDateDataList> models = new ArrayList<>();
    private ScheduleListener mListener;
    private ArrayList<String> scheduleDates;
    private String group_code;
    private Context context;

    public ScheduleRecycleviewAdapter(Context context, ArrayList<EventScheduleListDateDataList> models, ScheduleListener mListener, ArrayList<String> scheduleDates, String group_code) {
        this.context = context;
        this.models = models;
        this.mListener = mListener;
        this.scheduleDates = scheduleDates;
        this.group_code = group_code;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_schedule_adapter, parent, false);
        return new ScheduleRecycleviewAdapter.MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventScheduleListDateDataList model = models.get(position);
        holder.tvRvScheduleTimefvbi.setText(model.getWaktu());
        holder.tvRvScheduleTitlefvbi.setText(model.getSchedule_title());
        if (!TextUtils.isEmpty(model.getLocation())) {
            holder.llRvScheduleLocationfvbi.setVisibility(View.VISIBLE);
            holder.tvRvScheduleLocationTitlefvbi.setText(Html.fromHtml(model.getLocation()));
        } else {
            holder.llRvScheduleLocationfvbi.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model.getDetail())) {
            holder.bRvScheduleDetailfvbi.setVisibility(View.VISIBLE);
            holder.tvRvScheduleDetailfvbi.setText(Html.fromHtml(model.getDetail()));
        } else {
            holder.bRvScheduleDetailfvbi.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model.getLink())) {
            holder.bRvScheduleLiveQAfvbi.setVisibility(View.VISIBLE);
        } else {
            holder.bRvScheduleLiveQAfvbi.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getLinkvote())) {
            holder.bRvScheduleVotefvbi.setVisibility(View.VISIBLE);
        } else {
            holder.bRvScheduleVotefvbi.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getExternalframe().getLink()) && model.getExternalframe().getExternal().equalsIgnoreCase("yes")) {
            holder.bRvScheduleExternalfvbi.setText(model.getExternalframe().getName());
            holder.bRvScheduleExternalfvbi.setVisibility(View.VISIBLE);
        } else {
            holder.bRvScheduleExternalfvbi.setText(model.getExternalframe().getName());
            holder.bRvScheduleExternalfvbi.setVisibility(View.GONE);
        }

        holder.bRvScheduleDetailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bRvScheduleDetailfvbi.setVisibility(View.GONE);
                holder.tvRvScheduleDetailfvbi.setVisibility(View.VISIBLE);
            }
        });
        holder.tvRvScheduleDetailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bRvScheduleDetailfvbi.setVisibility(View.VISIBLE);
                holder.tvRvScheduleDetailfvbi.setVisibility(View.GONE);
            }
        });
        holder.bRvScheduleVotefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickVote(model.getLinkvote());
            }
        });
        holder.bRvScheduleLiveQAfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickQNA(model.getLink());
            }
        });
        holder.bRvScheduleExternalfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickExternal(model.getExternalframe().getLink());
            }
        });

        if (DateTimeUtil.isToday(DateTimeUtil.stringToDate(model.getDate(), new SimpleDateFormat("EEEE dd MMMM yyyy")))) {
            if (model.getWaktu().contains("-")) {
                String[] split = model.getWaktu().split("-");
                if (DateTimeUtil.isBetween2Time(split[0], split[1])) {
                    holder.tvRvScheduleTimefvbi.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    holder.ivRvScheduleIndicatorfvbi.setBackgroundResource(R.color.black);
                }
            }
        } else {
            holder.tvRvScheduleTimefvbi.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.ivRvScheduleIndicatorfvbi.setBackgroundResource(R.color.colorAccent);
        }

        if (position == getItemCount() - 1) {
            holder.vRvSchedulelinefvbi.setVisibility(View.GONE);
            holder.ivRvScheduleIndicatorfvbi.setBackgroundResource(R.color.colorPrimary);
        } else {
            holder.vRvSchedulelinefvbi.setVisibility(View.VISIBLE);
            holder.ivRvScheduleIndicatorfvbi.setBackgroundResource(R.color.colorAccent);
        }

        /*if (TextUtils.isEmpty(model.getDate())){
            holder.vRvSchedulelinefvbi.setVisibility(View.GONE);
            holder.llRvScheduleContentfvbi.setVisibility(View.GONE);
            holder.ivRvScheduleIndicatorfvbi.setBackgroundColor(R.color.colorPrimary);
        }*/

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LinearLayout llRvScheduleLocationfvbi;
        private final TextView tvRvScheduleTimefvbi, tvRvScheduleTitlefvbi, tvRvScheduleLocationTitlefvbi, tvRvScheduleDetailfvbi;
        private final Button bRvScheduleDetailfvbi, bRvScheduleLiveQAfvbi, bRvScheduleVotefvbi, bRvScheduleExternalfvbi;
        private final ImageView ivRvScheduleIndicatorfvbi;
        private final View vRvSchedulelinefvbi;
        private final LinearLayout llRvScheduleContentfvbi;
        private final LinearLayout llRvScheduleContent2fvbi;
        private final SQLiteHelper db;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            db = new SQLiteHelper(itemView.getContext());
            tvRvScheduleTimefvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleTime);
            tvRvScheduleTitlefvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleTitle);
            llRvScheduleLocationfvbi = (LinearLayout) itemView.findViewById(R.id.llRvScheduleLocation);
            tvRvScheduleLocationTitlefvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleLocationTitle);
            bRvScheduleDetailfvbi = (Button) itemView.findViewById(R.id.bRvScheduleDetail);
            tvRvScheduleDetailfvbi = (TextView) itemView.findViewById(R.id.tvRvScheduleDetail);
            bRvScheduleVotefvbi = (Button) itemView.findViewById(R.id.bRvScheduleVote);
            bRvScheduleLiveQAfvbi = (Button) itemView.findViewById(R.id.bRvScheduleLiveQA);
            bRvScheduleExternalfvbi = (Button) itemView.findViewById(R.id.bRvScheduleExternal);
            ivRvScheduleIndicatorfvbi = (ImageView) itemView.findViewById(R.id.ivRvScheduleIndicator);
            vRvSchedulelinefvbi = (View) itemView.findViewById(R.id.vRvScheduleline);
            llRvScheduleContentfvbi = (LinearLayout) itemView.findViewById(R.id.llRvScheduleContent);
            llRvScheduleContent2fvbi = (LinearLayout) itemView.findViewById(R.id.llRvScheduleContent2);

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
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
        notifyDataSetChanged();
        notifyItemRangeChanged(0, models.size());
    }

    public void replaceData(List<EventScheduleListDateDataList> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<EventScheduleListDateDataList> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public interface ScheduleListener {
        void onClickVote(String link);

        void onClickQNA(String link);

        void onClickExternal(String link);
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
