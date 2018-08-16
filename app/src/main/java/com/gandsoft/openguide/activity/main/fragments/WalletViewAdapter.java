package com.gandsoft.openguide.activity.main.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

                Document doc = Jsoup.parse(model.getBody_wallet());
                String h1 = doc.getElementsByTag("h1").first().text();
                Elements dd = doc.getElementsByTag("dd");
                Elements dt = doc.select("dt");
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter h1); : " + h1);
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dd.text()); : " + dd.text());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dd.size()); : " + dd.size());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dt.text()); : " + dt.text());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dt.size()); : " + dt.size());
                for (int i = 0; i < dd.size(); i++) {
                    Element element = dd.get(i);
                    Elements allElements = element.getAllElements();
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter element.getAllElements().size: " + element.getAllElements().size());
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter  allElements.get(0) : " + allElements.get(0).text());
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter  allElements.get(1) : " + allElements.get(1).text());
                }

            {
                if (!TextUtils.isEmpty(model.getDetail())) {
                    holder0.bWalletTransportDetailfvbi.setVisibility(View.VISIBLE);
                    holder0.tvWalletTransportDetailfvbi.setText(model.getDetail());
                } else {
                    holder0.bWalletTransportDetailfvbi.setVisibility(View.GONE);
                }
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

                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWalletTransportNotiffvbi, tvWalletTransportNamefvbi, tvWalletTransportTitleDeparturefvbi, tvWalletTransportAirportDeparturefvbi, tvWalletTransportDateDeparturefvbi, tvWalletTransportTimeDeparturefvbi, tvWalletTransportTitleArrivalfvbi, tvWalletTransportAirportArrivalfvbi, tvWalletTransportDateArrivalfvbi, tvWalletTransportTimeArrivalfvbi, tvWalletTransportDetailfvbi, tvWalletTransportFlightCodeTitlefvbi, tvWalletTransportFlightCodefvbi;
        private final Button bWalletTransportDetailfvbi;
        private final ImageView ivWalletTransportIconfvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWalletTransportNotiffvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportNotif);
            tvWalletTransportNamefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportName);
            tvWalletTransportTitleDeparturefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportTitleDeparture);
            tvWalletTransportAirportDeparturefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportAirportDeparture);
            tvWalletTransportDateDeparturefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportDateDeparture);
            tvWalletTransportTimeDeparturefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportTimeDeparture);
            tvWalletTransportTitleArrivalfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportTitleArrival);
            tvWalletTransportAirportArrivalfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportAirportArrival);
            tvWalletTransportDateArrivalfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportDateArrival);
            tvWalletTransportTimeArrivalfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportTimeArrival);
            tvWalletTransportDetailfvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportDetail);
            tvWalletTransportFlightCodeTitlefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportFlightCodeTitle);
            tvWalletTransportFlightCodefvbi = (TextView) itemView.findViewById(R.id.tvWalletTransportFlightCode);
            bWalletTransportDetailfvbi = (Button) itemView.findViewById(R.id.bWalletTransportDetail);
            ivWalletTransportIconfvbi = (ImageView) itemView.findViewById(R.id.ivWalletTransportIcon);
        }
    }

    private class ViewHolder1 extends ViewHolder {

        private final ImageView ivWalletIdCardfvbi;
        private final CardView cvWalletIdCardDetailfvbi;
        private final TextView tvWalletIdCardNamefvbi, tvWalletIdCardIdfvbi, tvWalletIdCardEventTitlefvbi, tvWalletIdCardEventPlacefvbi, tvWalletIdCardDatefvbi, tvWalletIdCardDetailfvbi;

        public ViewHolder1(View itemView) {
            super(itemView);
            ivWalletIdCardfvbi = (ImageView) itemView.findViewById(R.id.ivWalletIdCard);
            tvWalletIdCardNamefvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardName);
            tvWalletIdCardIdfvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardId);
            tvWalletIdCardEventTitlefvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardEventTitle);
            tvWalletIdCardEventPlacefvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardEventPlace);
            tvWalletIdCardDatefvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardDate);
            tvWalletIdCardDetailfvbi = (TextView) itemView.findViewById(R.id.tvWalletIdCardDetail);
            cvWalletIdCardDetailfvbi = (CardView) itemView.findViewById(R.id.cvWalletIdCardDetail);
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
