package com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentGetResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

class HomeContentCommentAdapter extends RecyclerView.Adapter<HomeContentCommentAdapter.ViewHolder> {

    private final Activity activity;
    private List<HomeContentPostCommentGetResponseModel> models = new ArrayList<>();
    private final EventTheEvent theEventModel;
    private final UserListEventResponseModel oneListEventModel;
    private final String accountId;
    private final String eventId;
    private HomeContentCommentListener mlistener;

    public HomeContentCommentAdapter(Activity activity, List<HomeContentPostCommentGetResponseModel> models, EventTheEvent theEventModel, UserListEventResponseModel oneListEventModel, String accountId, String eventId, HomeContentCommentListener mlistener) {

        this.activity = activity;
        this.models = models;
        this.theEventModel = theEventModel;
        this.oneListEventModel = oneListEventModel;
        this.accountId = accountId;
        this.eventId = eventId;
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeContentPostCommentGetResponseModel model = models.get(position);

        holder.tvRVCommentUsernamefvbi.setText(model.getFull_name());
        holder.tvRVCommentTimefvbi.setText(model.getPost_time());
        holder.tvRVCommentContentfvbi.setText(model.getPost_content());

        Glide.with(activity)
                .load(model.getImage_icon())
                .placeholder(R.drawable.template_account_og)
                .skipMemoryCache(true)
                .error(R.drawable.template_account_og)
                .into(holder.ivRVCommentIconfvbi);

        if (Integer.parseInt(theEventModel.getDeletepost_status()) == 1 || model.getAccount_id().equalsIgnoreCase(accountId)) {
            holder.llRVCommentRemovefvbi.setVisibility(View.VISIBLE);
        }

        holder.llRVCommentRemovefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onDelete(model.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRVCommentUsernamefvbi;
        private final TextView tvRVCommentTimefvbi;
        private final TextView tvRVCommentContentfvbi;
        private final LinearLayout llRVCommentRemovefvbi;
        private final ImageView ivRVCommentIconfvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRVCommentUsernamefvbi = (TextView) itemView.findViewById(R.id.tvRVCommentUsername);
            tvRVCommentContentfvbi = (TextView) itemView.findViewById(R.id.tvRVCommentContent);
            tvRVCommentTimefvbi = (TextView) itemView.findViewById(R.id.tvRVCommentTime);
            llRVCommentRemovefvbi = (LinearLayout) itemView.findViewById(R.id.llRVCommentRemove);
            ivRVCommentIconfvbi = (ImageView) itemView.findViewById(R.id.ivRVCommentIcon);
        }
    }

    public void setData(List<HomeContentPostCommentGetResponseModel> datas) {
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<HomeContentPostCommentGetResponseModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeContentPostCommentGetResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public void addDataFirst(HomeContentPostCommentGetResponseModel data) {
        int insertIndex = 0;
        models.add(insertIndex, data);
        notifyItemInserted(insertIndex);
    }

    public void removeDataFirst() {
        int removeIndex = 0;
        models.remove(removeIndex);
        notifyItemRemoved(removeIndex);
    }

    public void removeAt(int position) {
        int removeIndex = position;
        models.remove(removeIndex);
        notifyItemRemoved(removeIndex);
    }

    interface HomeContentCommentListener {
        void onDelete(String commentId, int position);
    }
}