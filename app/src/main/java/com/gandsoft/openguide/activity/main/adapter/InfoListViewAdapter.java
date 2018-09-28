package com.gandsoft.openguide.activity.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
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

    @SuppressLint({"NewApi", "SetTextI18n"})
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
            Log.d("holder eventid", holder.eventId);
            ArrayList<EventCommitteeNote> commiteNote = holder.db.getCommiteNote(holder.eventId);
            int noteIsOpen = holder.db.getCommiteHasBeenOpened(holder.eventId);
            if (commiteNote.size() != 0) {
                holder.tvListInfofvbi.setText("Inbox" + " (" + noteIsOpen + "/" + commiteNote.size() + ")");
                if (commiteNote.size() == noteIsOpen) {
                    holder.ivListInfoAlert.setVisibility(View.GONE);
                } else {
                    holder.ivListInfoAlert.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvListInfofvbi.setText("Inbox (0/0)");
                holder.tvListInfofvbi.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                holder.llListInfofvbi.setEnabled(false);
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
        ImageView ivListInfoAlert;

        SQLiteHelper db;
        String eventId, accountId;

        public ViewHolder(View itemView) {

            super(itemView);
            db = new SQLiteHelper(itemView.getContext());
            llListInfofvbi = (LinearLayout) itemView.findViewById(R.id.llListInfo);
            ivListInfofvbi = (ImageView) itemView.findViewById(R.id.ivListInfo);
            tvListInfofvbi = (TextView) itemView.findViewById(R.id.tvListInfo);
            ivListInfoAlert = (ImageView) itemView.findViewById(R.id.ivListInfoAlert);
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        }
    }

    public void setData(List<InfoListviewModel> datas) {
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
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

    public void deleteData(List<Integer> index) {
        int[] ints = ListArrayUtil.convertListIntegertToIntArray(index);
        for (int anInt : ints) {
            models.remove(anInt);
        }
        notifyDataSetChanged();
    }
}
