package com.gandsoft.openguide.activity.main.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

class WalletViewAdapter extends RecyclerView.Adapter<WalletViewAdapter.ViewHolder> {
    private static final int TYPE_TRANSPORT = 1;
    private static final int TYPE_IDCARD = 2;
    private WalletViewHook context;
    private List<UserWalletDataResponseModel> models = new ArrayList<>();

    public WalletViewAdapter(Context context, List<UserWalletDataResponseModel> models) {
        try {
            this.context = (WalletViewHook) context;
        } catch (Exception e) {
            this.context = null;
        }
        this.models = models;
    }

    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String notif = models.get(position).getNotif();
        if (notif.equalsIgnoreCase("INBOUND") || notif.equalsIgnoreCase("OUTBOUND")) {
            return TYPE_TRANSPORT;
        } else {
            return TYPE_IDCARD;
        }
    }

    @NonNull
    @Override
    public WalletViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TRANSPORT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_wallet_transport_adapter, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_wallet_account_adapter, parent, false);
            return new ViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewAdapter.ViewHolder holder, int position) {
        UserWalletDataResponseModel model = models.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_TRANSPORT:
                ViewHolder holder0 = (ViewHolder) holder;
                holder0.tvWalletTransportNotiffvbi.setText(model.getNotif());
                holder0.tvWalletTransportHtmlfvbi.setText(Html.fromHtml(model.getBody_wallet()));
                holder0.wvWalletTransportHtmlfvbi.getSettings().setJavaScriptEnabled(true);
                holder0.wvWalletTransportHtmlfvbi.loadDataWithBaseURL("", model.getBody_wallet(), "text/html", "UTF-8", "");
                if (!model.getDetail().isEmpty() && model.getDetail() != null) {
                    holder0.bWalletTransportDetailfvbi.setVisibility(View.VISIBLE);
                    holder0.tvWalletTransportDetailfvbi.setText(model.getDetail());
                } else {
                    holder0.bWalletTransportDetailfvbi.setVisibility(View.GONE);
                }
                holder0.bWalletTransportDetailfvbi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder0.bWalletTransportDetailfvbi.setVisibility(View.GONE);
                        holder0.tvWalletTransportDetailfvbi.setVisibility(View.VISIBLE);
                    }
                });
                holder0.tvWalletTransportDetailfvbi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder0.bWalletTransportDetailfvbi.setVisibility(View.VISIBLE);
                        holder0.tvWalletTransportDetailfvbi.setVisibility(View.GONE);
                    }
                });
                break;
            case TYPE_IDCARD:
                ViewHolder1 holder1 = (ViewHolder1) holder;
                holder1.tvWalletIdCardHtmlfvbi.setText(Html.fromHtml(model.getBody_wallet()));
                holder1.wvWalletIdCardHtmlfvbi.getSettings().setJavaScriptEnabled(true);
                holder1.wvWalletIdCardHtmlfvbi.loadDataWithBaseURL("", model.getBody_wallet(), "text/html", "UTF-8", "");

                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWalletTransportNotiffvbi;
        private final TextView tvWalletTransportHtmlfvbi;
        private final Button bWalletTransportDetailfvbi;
        private final TextView tvWalletTransportDetailfvbi;
        private final WebView wvWalletTransportHtmlfvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWalletTransportNotiffvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportNotif);
            tvWalletTransportHtmlfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportHtml);
            bWalletTransportDetailfvbi = (Button) itemView.findViewById(R.id.bWalletTransportDetail);
            tvWalletTransportDetailfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportDetail);
            wvWalletTransportHtmlfvbi = (WebView) itemView.findViewById(R.id.wvWalletTransportHtml);
        }
    }

    private class ViewHolder1 extends ViewHolder {
        private final TextView tvWalletIdCardHtmlfvbi;
        private final CardView cvWalletIDcardDetailfvbi;
        private final WebView wvWalletIdCardHtmlfvbi;

        public ViewHolder1(View itemView) {
            super(itemView);
            tvWalletIdCardHtmlfvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardHtml);
            cvWalletIDcardDetailfvbi = (CardView) itemView.findViewById(R.id.cvWalletIDcardDetail);
            wvWalletIdCardHtmlfvbi = (WebView) itemView.findViewById(R.id.wvWalletIdCardHtml);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<UserWalletDataResponseModel> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<UserWalletDataResponseModel> datas) {
        if (models.size() > 0) {
            models.clear();
            models.addAll(datas);
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<UserWalletDataResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
