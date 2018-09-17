package com.gandsoft.openguide.activity.main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.ChangeEventPastHook;
import com.gandsoft.openguide.activity.infomenu.cInboxActivity;
import com.gandsoft.openguide.activity.main.fragments.eInfoFragment;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.DeviceDetailUtil;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class InfoListViewAdapter extends RecyclerView.Adapter<InfoListViewAdapter.ViewHolder> {
    private List<InfoListviewModel> models = new ArrayList<>();
    private Context context;
    private ListAdapterListener mListener;

    public interface ListAdapterListener { // create an interface
        void click(String position); // create callback function
    }

    public InfoListViewAdapter(Context context, List<InfoListviewModel> items, ListAdapterListener mListener) {
        this.context = context;
        this.mListener = mListener;
        models = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_info_adapter, parent, false);
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


        if (models.get(position).getTitle().equals("Inbox")) {
            Log.d("holder eventid",holder.eventId);
            ArrayList<EventCommitteeNote> wew =  holder.db.getCommiteNote(holder.eventId);
            int zz =0;
            try {
                if(!wew.isEmpty()) {
                    for (int ux = 0; ux < wew.size(); ux++) {
                        Log.d("get db error", wew.get(ux).getHas_been_opened());
                        Log.d("get db error", wew.get(1).getHas_been_opened());
                        if (wew.get(ux).getHas_been_opened().equals("1")) {
                            zz++;
                        }
                    }
                    if(zz>0) {
                        holder.tvListInfofvbi.setText("Inbox" + " (" + zz + "/" + wew.size() + ")");
                        holder.ivAlertNotif.setVisibility(View.VISIBLE);
                    }else {
                        holder.tvListInfofvbi.setText("Inbox" + " (" + 0 + "/" + wew.size() + ")");
                    }
                }else{
                    holder.tvListInfofvbi.setText("Inbox (0/0)");
                    holder.llListInfofvbi.setClickable(false);
                }
            }catch (Exception w){
                Log.d("inbox error ",String.valueOf(w));
                holder.tvListInfofvbi.setText("Inbox (0/0)");
                holder.llListInfofvbi.setClickable(false);
            }
        }

        holder.llListInfofvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.click(model.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llListInfofvbi;
        ImageView ivListInfofvbi;
        TextView tvListInfofvbi;
        ImageView ivAlertNotif;

        SQLiteHelper db;
        String eventId,accountId;
        public ViewHolder(View itemView) {

            super(itemView);
            db = new SQLiteHelper(itemView.getContext());
            llListInfofvbi = (LinearLayout) itemView.findViewById(R.id.llListInfo);
            ivListInfofvbi = (ImageView) itemView.findViewById(R.id.ivListInfo);
            tvListInfofvbi = (TextView) itemView.findViewById(R.id.tvListInfo);
            ivAlertNotif = (ImageView) itemView.findViewById(R.id.ivListInfoAlert);
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        }
    }

    public void setData(List<InfoListviewModel> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<InfoListviewModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<InfoListviewModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public void deleteData(List<Integer> index){
        int[] ints = ListArrayUtil.convertListIntegertToIntArray(index);
        for (int anInt : ints) {
            models.remove(anInt);
        }
        notifyDataSetChanged();
    }
}
