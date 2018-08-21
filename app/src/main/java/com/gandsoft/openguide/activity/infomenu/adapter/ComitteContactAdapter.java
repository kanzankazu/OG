package com.gandsoft.openguide.activity.infomenu.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class ComitteContactAdapter extends RecyclerView.Adapter<ComitteContactAdapter.MyViewHolder> {
    private ComitteContactHook parent;
    private List<EventDataContactList> models = new ArrayList<>();


    public ComitteContactAdapter(Activity parent, ArrayList<EventDataContactList> models) {
        this.models = models;
        try {
            this.parent = (ComitteContactHook) parent;
        } catch (Exception e) {
            this.parent = null;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_info_comitte_contact, parent, false);
        return new ComitteContactAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventDataContactList model = models.get(position);
        Log.d("Lihat", "onBindViewHolder ComitteContactAdapter : " + position);
        Log.d("Lihat", "onBindViewHolder ComitteContactAdapter : " + model.getTitle());
        Log.d("Lihat", "onBindViewHolder ComitteContactAdapter : " + model.getTelephone());
        Log.d("Lihat", "onBindViewHolder ComitteContactAdapter : " + model.getName());
        Log.d("Lihat", "onBindViewHolder ComitteContactAdapter : " + model.getEmail());
        if (position > 0) {
            if (model.getTitle().equalsIgnoreCase(models.get(position - 1).getTitle())) {
                holder.llComitteEventfvbi.setVisibility(View.GONE);
                holder.vComittelinefvbi.setVisibility(View.GONE);
            } else {
                holder.llComitteEventfvbi.setVisibility(View.VISIBLE);
                holder.vComittelinefvbi.setVisibility(View.VISIBLE);
            }
        } else {
            holder.llComitteEventfvbi.setVisibility(View.VISIBLE);
            holder.vComittelinefvbi.setVisibility(View.VISIBLE);
        }

        holder.tvComitteEventTitlevbi.setText(Html.fromHtml(model.getTitle()));
        holder.tvComitteNamefvbi.setText(model.getName());
        holder.tvComitteEmailvbi.setText(model.getEmail());
        SystemUtil.setUnderlineTextView(holder.tvComitteEmailvbi);
        holder.tvComitteNumberfvbi.setText(model.getTelephone());
        SystemUtil.setUnderlineTextView(holder.tvComitteNumberfvbi);

        if (!TextUtils.isEmpty(model.getName())) {
            holder.tvComitteNamefvbi.setVisibility(View.VISIBLE);
        } else {
            holder.tvComitteNamefvbi.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getEmail())) {
            holder.tvComitteEmailvbi.setVisibility(View.VISIBLE);
        } else {
            holder.tvComitteEmailvbi.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getTelephone())) {
            holder.tvComitteNumberfvbi.setVisibility(View.VISIBLE);
        } else {
            holder.tvComitteNumberfvbi.setVisibility(View.GONE);
        }
        holder.tvComitteNumberfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.dialNumber(model.getTelephone());
            }
        });
        holder.tvComitteEmailvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.sendEmail(model.getEmail());
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout llComitteEventfvbi;
        private final TextView tvComitteEventTitlevbi;
        private final TextView tvComitteNamefvbi;
        private final TextView tvComitteEmailvbi;
        private final TextView tvComitteNumberfvbi;
        private final View vComittelinefvbi;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            llComitteEventfvbi = (LinearLayout) itemView.findViewById(R.id.llComitteEvent);
            tvComitteEventTitlevbi = (TextView) itemView.findViewById(R.id.tvComitteEventTitle);
            tvComitteNamefvbi = (TextView) itemView.findViewById(R.id.tvComitteName);
            tvComitteEmailvbi = (TextView) itemView.findViewById(R.id.tvComitteEmail);
            tvComitteNumberfvbi = (TextView) itemView.findViewById(R.id.tvComitteNumber);
            vComittelinefvbi = (View) itemView.findViewById(R.id.vComitteline);

        }

    }

    public void removeAt(int position) {
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, models.size());
    }

    public void setData(List<EventDataContactList> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<EventDataContactList> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<EventDataContactList> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
