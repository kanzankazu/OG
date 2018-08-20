package com.gandsoft.openguide.activity.infomenu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class ComitteContactAdapter extends RecyclerView.Adapter<ComitteContactAdapter.MyViewHolder> {
    private List<EventDataContactList> models;
    private Context context;


    public ComitteContactAdapter(Context context, ArrayList<EventDataContactList> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_info_comitte_contact, parent, false);
        return new ComitteContactAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventDataContactList model = models.get(position);
        holder.tvComitteEventfvbi.setText(Html.fromHtml(model.getTitle()));
        holder.tvComitteEmailfvbi.setText(model.getEmail());
        holder.tvComitteNumberfvbi.setText(model.getTelephone());
        holder.tvComitteNamefvbi.setText(model.getName());
        if (TextUtils.isEmpty(model.getEmail())) {
            holder.tvComitteEmailfvbi.setVisibility(View.VISIBLE);
        }else {
            holder.tvComitteEmailfvbi.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(model.getName())) {
            holder.tvComitteNamefvbi.setVisibility(View.VISIBLE);
        }else {
            holder.tvComitteNamefvbi.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(model.getEmail())) {
            holder.tvComitteNumberfvbi.setVisibility(View.VISIBLE);
        }else {
            holder.tvComitteNumberfvbi.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivComitteIconfvbi;
        private TextView tvComitteTitlefvbi,tvComitteEventfvbi,tvComitteNamefvbi,tvComitteNumberfvbi,tvComitteEmailfvbi;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            ivComitteIconfvbi = (ImageView)itemView.findViewById(R.id.ivComitteIcon);
            tvComitteTitlefvbi = (TextView)itemView.findViewById(R.id.tvComitteTitle);
            tvComitteEventfvbi = (TextView)itemView.findViewById(R.id.tvComitteEvent);
            tvComitteNamefvbi = (TextView)itemView.findViewById(R.id.tvComitteName);
            tvComitteNumberfvbi = (TextView)itemView.findViewById(R.id.tvComitteNumber);
            tvComitteEmailfvbi = (TextView)itemView.findViewById(R.id.tvComitteEmail);

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

    public void setData(List<EventDataContactList> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<EventDataContactList> datas) {
        models.clear();
        models = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<EventDataContactList> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
