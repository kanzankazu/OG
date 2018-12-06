package com.gandsoft.openguide.view.main.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.File;

public class ChatGroupAddSelectAdapter extends RecyclerView.Adapter<ChatGroupAddSelectAdapter.ViewHolder> {

    private Activity activity;
    private ChatGroupAddSelectListener mListener;

    public ChatGroupAddSelectAdapter(Activity activity, ChatGroupAddSelectListener mListener) {

        this.activity = activity;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_chat_group_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvRVChatGroupAddNamefvbi.setText("");
        holder.tvRVChatGroupAddPhoneNofvbi.setText("");

        String imageString = AppUtil.validationStringImageIcon(activity, "", "", true);
        Glide.with(activity)
                .load(InputValidUtil.isLinkUrl(imageString) ? imageString : new File(imageString))
                .asBitmap()
                .placeholder(R.drawable.template_account_og)
                .error(R.drawable.template_account_og)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.civRVChatGroupAddPhotofvbi.setImageBitmap(resource);
                    }
                });

        ((ViewHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewHolder) holder).cbRVChatGroupAddSelectfvbi.setChecked(
                        !((ViewHolder) holder).cbRVChatGroupAddSelectfvbi.isChecked());
                if (((ViewHolder) holder).cbRVChatGroupAddSelectfvbi.isChecked()) {
                    mListener.onItemCheck(/*currentItem*/);
                } else {
                    mListener.onItemUncheck(/*currentItem*/);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cbRVChatGroupAddSelectfvbi;
        private final TextView tvRVChatGroupAddNamefvbi;
        private final TextView tvRVChatGroupAddPhoneNofvbi;
        private final CircularImageView civRVChatGroupAddPhotofvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            cbRVChatGroupAddSelectfvbi = (CheckBox) itemView.findViewById(R.id.cbRVChatGroupAddSelect);
            tvRVChatGroupAddNamefvbi = (TextView) itemView.findViewById(R.id.tvRVChatGroupAddName);
            tvRVChatGroupAddPhoneNofvbi = (TextView) itemView.findViewById(R.id.tvRVChatGroupAddPhoneNo);
            civRVChatGroupAddPhotofvbi = (CircularImageView) itemView.findViewById(R.id.civRVChatGroupAddPhoto);

        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    public interface ChatGroupAddSelectListener {
        void onItemCheck();

        void onItemUncheck();
    }
}
