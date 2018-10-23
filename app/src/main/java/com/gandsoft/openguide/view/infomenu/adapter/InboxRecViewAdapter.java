package com.gandsoft.openguide.activity.infomenu.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

public class InboxRecViewAdapter extends RecyclerView.Adapter<InboxRecViewAdapter.ViewHolder> {

    private Activity activity;
    private List<EventCommitteeNote> models;
    private final String accountId;
    private final String eventId;
    private final SQLiteHelper db;

    public InboxRecViewAdapter(Activity activity, ArrayList<EventCommitteeNote> models, String accountId, String eventId, SQLiteHelper db) {
        this.activity = activity;
        this.models = models;
        this.accountId = accountId;
        this.eventId = eventId;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_inbox, parent, false);
        return new InboxRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventCommitteeNote model = models.get(position);
        holder.tvInboxTitlefvbi.setText(model.getTitle());
        holder.tvInboxDateTimefvbi.setText(model.getTanggal());
        holder.tvInboxDetailfvbi.setHtml(model.getNote());
        Log.d("Lihat", "onBindViewHolder InboxRecViewAdapter : " + model.getHas_been_opened());

        if (!TextUtils.isEmpty(model.getHas_been_opened())) {
            if (model.getHas_been_opened().equalsIgnoreCase("1")) {
                holder.tvInboxTitlefvbi.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                holder.tvInboxTitlefvbi.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            }
        } else {
            holder.tvInboxTitlefvbi.setTextColor(activity.getResources().getColor(R.color.colorAccent));
        }

        holder.bInboxDetailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateOneKey(SQLiteHelper.TableCommiteNote, SQLiteHelper.Key_CommiteNote_Id, model.getId(), SQLiteHelper.Key_CommiteNote_Has_been_opened, String.valueOf(1));
                holder.tvInboxTitlefvbi.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                holder.bInboxDetailfvbi.setVisibility(View.GONE);
                holder.llInboxNotefvbi.setVisibility(View.VISIBLE);
            }
        });
        holder.tvInboxDetailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bInboxDetailfvbi.setVisibility(View.VISIBLE);
                holder.llInboxNotefvbi.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvInboxTitlefvbi;
        private final HtmlTextView tvInboxDetailfvbi;
        private final TextView tvInboxDateTimefvbi;
        private final Button bInboxDetailfvbi;
        private final LinearLayout llInboxNotefvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvInboxTitlefvbi = (TextView) itemView.findViewById(R.id.tvInboxTitle);
            tvInboxDetailfvbi = (HtmlTextView) itemView.findViewById(R.id.tvInboxDetail);
            tvInboxDateTimefvbi = (TextView) itemView.findViewById(R.id.tvInboxDateTime);
            bInboxDetailfvbi = (Button) itemView.findViewById(R.id.bInboxDetail);
            llInboxNotefvbi = (LinearLayout) itemView.findViewById(R.id.llInboxNote);
        }
    }

    public void setData(List<EventCommitteeNote> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<EventCommitteeNote> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<EventCommitteeNote> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
