package com.gandsoft.openguide.view.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentGetResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class HomeContentCommentAdapter extends RecyclerView.Adapter<HomeContentCommentAdapter.ViewHolder> {

    private final Activity activity;
    private final EventTheEvent theEventModel;
    private final UserListEventResponseModel oneListEventModel;
    private final String accountId;
    private final String eventId;
    private List<HomeContentPostCommentGetResponseModel> models = new ArrayList<>();
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
        holder.tvRVCommentContentfvbi.setText(model.getPost_content());
        holder.tvRVCommentTimefvbi.setText(DateTimeUtil.getTimeAgo(DateTimeUtil.stringToDate(model.getPost_time(), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"))));

        Glide.with(activity)
                .load(R.drawable.load)
                .asGif()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(holder.ivRVCommentIconfvbi);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Glide.with(activity)
                        .load(model.getImage_icon())
                        .asBitmap()
                        .error(R.drawable.template_account_og)
                        .placeholder(R.drawable.ic_action_name)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                holder.ivRVCommentIconfvbi.setImageBitmap(resource);
                                if (NetworkUtil.isConnected(activity)) {
                                    String imageCachePath = PictureUtil.saveImageHomeContentIcon(activity, resource, model.getAccount_id() + "_icon_comment", eventId);
                                    holder.db.saveCommentIcon(imageCachePath, accountId, eventId, model.getId());
                                }
                            }
                        });
            }
        }, 2000);

        if ((Integer.parseInt(theEventModel.getDeletepost_status()) == 1 && model.getAccount_id().equalsIgnoreCase(accountId)) || oneListEventModel.getRole_name().equalsIgnoreCase("ADMIN")) {
            holder.llRVCommentRemovefvbi.setVisibility(View.VISIBLE);
        }

        holder.llRVCommentRemovefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(activity)) {
                    mlistener.onDelete(model.getId(), position);
                } else {
                    SystemUtil.showToast(activity, "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
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
        notifyItemRangeChanged(removeIndex, models.size());
    }

    public void removeAt(int position) {
        int removeIndex = position;
        models.remove(removeIndex);
        notifyItemRemoved(removeIndex);
        notifyItemRangeChanged(position, models.size());
    }

    interface HomeContentCommentListener {
        void onDelete(String commentId, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRVCommentUsernamefvbi;
        private final TextView tvRVCommentTimefvbi;
        private final TextView tvRVCommentContentfvbi;
        private final LinearLayout llRVCommentRemovefvbi;
        private final ImageView ivRVCommentIconfvbi;
        SQLiteHelperMethod db = new SQLiteHelperMethod(itemView.getContext());

        public ViewHolder(View itemView) {
            super(itemView);
            tvRVCommentUsernamefvbi = (TextView) itemView.findViewById(R.id.tvRVCommentUsername);
            tvRVCommentContentfvbi = (TextView) itemView.findViewById(R.id.tvRVCommentContent);
            tvRVCommentTimefvbi = (TextView) itemView.findViewById(R.id.tvRVCommentTime);
            llRVCommentRemovefvbi = (LinearLayout) itemView.findViewById(R.id.llRVCommentRemove);
            ivRVCommentIconfvbi = (ImageView) itemView.findViewById(R.id.ivRVCommentIcon);
        }
    }
}
