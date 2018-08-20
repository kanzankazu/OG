package com.gandsoft.openguide.activity.main.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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

import com.bumptech.glide.Glide;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

class WalletViewAdapter extends RecyclerView.Adapter<WalletViewAdapter.ViewHolder> {
    private static final int TYPE_IDCARD = 1;
    private static final int TYPE_TRANSPORT_FLIGHT = 2;
    private WalletViewHook context;
    private List<UserWalletDataResponseModel> models = new ArrayList<>();
    private FragmentActivity fragment;

    public WalletViewAdapter(FragmentActivity fragmentActivity, Context context, List<UserWalletDataResponseModel> models) {
        fragment = fragmentActivity;
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
        String type = models.get(position).getType();
        if (notif.equalsIgnoreCase("INBOUND") || notif.equalsIgnoreCase("OUTBOUND")) {
            if (type.equalsIgnoreCase("flight")) {
                return TYPE_TRANSPORT_FLIGHT;
            }
        } else {
            return TYPE_IDCARD;
        }
        return TYPE_IDCARD;
    }

    @NonNull
    @Override
    public WalletViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_IDCARD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_wallet_account_adapter, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == TYPE_TRANSPORT_FLIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_wallet_transport_adapter, parent, false);
            return new ViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewAdapter.ViewHolder holder, int position) {
        UserWalletDataResponseModel model = models.get(position);

        switch (holder.getItemViewType()) {
            case TYPE_IDCARD:
                ViewHolder1 holder1 = (ViewHolder1) holder;
                Document doc1 = Jsoup.parse(model.getBody_wallet());
                String imgUrl = doc1.select("img").first().absUrl("src");
                String name = doc1.select("h1").text();
                String accountId = doc1.select("h2").text();
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + doc1.select("h3").text());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + doc1.select("h3"));

                /*String[] split = doc1.select("h3").text().split("<br>");
                String eventName = split[0];
                String eventLocation = split[1];
                String eventDate = split[2];*/

                Glide.with(fragment)
                        .load(imgUrl)
                        .placeholder(R.drawable.template_account_og)
                        .skipMemoryCache(true)
                        .error(R.drawable.template_account_og)
                        .into(holder1.ivWalletIdCardfvbi);
                holder1.tvWalletIdCardNamefvbi.setText(name);
                holder1.tvWalletIdCardIdfvbi.setText(accountId);
                /*holder1.tvWalletIdCardEventTitlefvbi.setText(eventName);
                holder1.tvWalletIdCardEventPlacefvbi.setText(eventLocation);
                holder1.tvWalletIdCardDatefvbi.setText(eventDate);*/

                break;
            case TYPE_TRANSPORT_FLIGHT:
                ViewHolder holder0 = (ViewHolder) holder;
                holder0.tvWalletTransportNotiffvbi.setText(model.getNotif());

                Document doc = Jsoup.parse(model.getBody_wallet());
                String nameFlight = doc.select("h1").text();
                Elements dds = doc.select("dd");
                Elements dts = doc.select("dt");
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter h1); : " + nameFlight);
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dd.text()); : " + dds.text());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dd.size()); : " + dds.size());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dt.text()); : " + dts.text());
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter dt.size()); : " + dts.size());
                for (Element dd : dds) {
                    Elements h2s = dd.select("h2");
                    String titleFlight = null;
                    String flightDate = null;
                    for (Element h2 : h2s) {
                        Log.d("Lihat", "onBindViewHolder WalletViewAdapter h2.nextSibling().toString : " + h2.siblingNodes().toString());
                        titleFlight = h2.siblingNodes().get(0).toString();
                        flightDate = h2.siblingNodes().get(2).toString();
                    }
                    String flightAirport = dd.select("h2").text();
                    String flightTime = dd.select("h3").text();
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + titleFlight);
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + flightDate);
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + flightAirport);
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + flightTime);
                    if (!TextUtils.isEmpty(flightAirport) && !TextUtils.isEmpty(flightTime)) {
                        if (titleFlight.equalsIgnoreCase("Departure")) {
                            holder0.tvWalletTransportTitleDeparturefvbi.setText(titleFlight);
                            holder0.tvWalletTransportAirportDeparturefvbi.setText(flightAirport);
                            holder0.tvWalletTransportDateDeparturefvbi.setText(flightDate);
                            holder0.tvWalletTransportTimeDeparturefvbi.setText(flightTime);
                        } else if (titleFlight.equalsIgnoreCase("Arrival")) {
                            holder0.tvWalletTransportTitleArrivalfvbi.setText(titleFlight);
                            holder0.tvWalletTransportAirportArrivalfvbi.setText(flightAirport);
                            holder0.tvWalletTransportDateArrivalfvbi.setText(flightDate);
                            holder0.tvWalletTransportTimeArrivalfvbi.setText(flightTime);
                        }
                    }
                }
                for (Element dt : dts) {
                    Elements bs = dt.select("b");
                    Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + dt.select("h3").text());
                    String flightCodeTitle = null;
                    for (Element b : bs) {
                        Log.d("Lihat", "onBindViewHolder WalletViewAdapter b.siblingNodes()) : " + b.siblingNodes().toString());
                        flightCodeTitle = b.siblingNodes().get(0).toString();
                    }
                    String flightCode = bs.text();
                    holder0.tvWalletTransportFlightCodeTitlefvbi.setText(flightCodeTitle);
                    holder0.tvWalletTransportFlightCodefvbi.setText(flightCode);
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
