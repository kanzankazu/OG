package com.gandsoft.openguide.activity.main.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class WalletViewAdapter extends RecyclerView.Adapter<WalletViewAdapter.ViewHolder> {
    private static final int TYPE_IDCARD = 1;
    private static final int TYPE_TRANSPORT = 2;
    private WalletViewHook context;
    private List<UserWalletDataResponseModel> models = new ArrayList<>();
    private FragmentActivity activity;
    private String eventId;

    public WalletViewAdapter(FragmentActivity activity, Context context, List<UserWalletDataResponseModel> models, String eventId) {
        this.activity = activity;
        this.eventId = eventId;
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
                return TYPE_TRANSPORT;
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
        } else if (viewType == TYPE_TRANSPORT) {
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
                Glide.with(activity)
                        .load(imgUrl)
                        .placeholder(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .error(R.drawable.template_account_og)
                        .into(holder1.ivWalletIdCardfvbi);
                holder1.tvWalletIdCardNamefvbi.setText(name);
                holder1.tvWalletIdCardIdfvbi.setText(accountId);

                Elements h3s = doc1.select("h3");
                for (Element h3 : h3s) {
                    Element br0 = h3.select("br").get(0);
                    holder1.tvWalletIdCardEventTitlefvbi.setText(Html.fromHtml(br0.siblingNodes().get(0).toString()));
                    holder1.tvWalletIdCardEventPlacefvbi.setText(Html.fromHtml(br0.siblingNodes().get(1).toString()));
                    holder1.tvWalletIdCardDatefvbi.setText(Html.fromHtml(br0.siblingNodes().get(3).toString()));
                }

            {
                if (!TextUtils.isEmpty(model.getDetail())) {
                    holder1.cvWalletIdCardDetailfvbi.setVisibility(View.VISIBLE);
                    holder1.tvWalletIdCardDetailfvbi.setText(model.getDetail());
                } else {
                    holder1.cvWalletIdCardDetailfvbi.setVisibility(View.GONE);
                }
            }
            holder1.cvWalletIdCardDetailfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder1.cvWalletIdCardDetailfvbi.setVisibility(View.GONE);
                    holder1.tvWalletIdCardDetailfvbi.setVisibility(View.VISIBLE);
                }
            });
            holder1.tvWalletIdCardDetailfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder1.cvWalletIdCardDetailfvbi.setVisibility(View.VISIBLE);
                    holder1.tvWalletIdCardDetailfvbi.setVisibility(View.GONE);
                }
            });

            break;
            case TYPE_TRANSPORT:
                ViewHolder holder0 = (ViewHolder) holder;
                holder0.tvWalletTransportNotiffvbi.setText(model.getNotif());

                Document doc = Jsoup.parse(model.getBody_wallet().replaceAll("&([^;]+?);", "**$1;"));
                doc.outputSettings()
                        .prettyPrint(false)
                        .escapeMode(Entities.EscapeMode.extended);
                String wallUserName = doc.select("h1").get(0).text();
                holder0.tvWalletTransportNamefvbi.setText(wallUserName);

                String wallAsciiIcon = doc.select("b").get(0).text().replaceAll("\\*\\*([^;]+?);", "&$1;");
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter wallAsciiIcon: " + wallAsciiIcon);
                int[] icon = new int[]{R.drawable.ic_car, R.drawable.ic_bus, R.drawable.ic_train, R.drawable.ic_boat, R.drawable.ic_airplane};
                String[] asciiCode = new String[]{"&#xE531;", "&#xE530;", "&#xe570;", "&#xE532;", "&#xe904;",};
                ArrayList<String> list = ListArrayUtil.convertStringArrayToListString(asciiCode);
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + list);
                int posStringInList = ListArrayUtil.getPosStringInList(list, wallAsciiIcon);
                Log.d("Lihat", "onBindViewHolder WalletViewAdapter : " + posStringInList);
                holder0.ivWalletTransportIconfvbi.setImageResource(icon[posStringInList]);

                String dest = doc.select("dd").get(0).text();
                String[] dests = dest.split(" ");
                String titleDest = dests[0];
                String destination = dests[1];
                String dateDest = dests[2];
                String timeDest = dests[3];
                holder0.tvWalletTransportTitleDeparturefvbi.setText(titleDest);
                holder0.tvWalletTransportDateDeparturefvbi.setText(dateDest);
                /*if (destination.equalsIgnoreCase("-")&&timeDest.equalsIgnoreCase("-")) {
                    holder0.tvWalletTransportAirportDeparturefvbi.setVisibility(View.GONE);
                    holder0.tvWalletTransportTimeDeparturefvbi.setVisibility(View.GONE);
                } else {
                    holder0.tvWalletTransportAirportDeparturefvbi.setText(destination);
                    holder0.tvWalletTransportTimeDeparturefvbi.setText(timeDest);
                }*/
                holder0.tvWalletTransportAirportDeparturefvbi.setText(destination);
                holder0.tvWalletTransportTimeDeparturefvbi.setText(timeDest);

                String arrv = doc.select("dd").get(2).text();
                String[] arrvs = arrv.split(" ");
                String titleArrv = arrvs[0];
                String arrival = arrvs[1];
                String dateArrv = arrvs[2];
                String timeArrv = arrvs[3];
                holder0.tvWalletTransportTitleArrivalfvbi.setText(titleArrv);
                holder0.tvWalletTransportDateArrivalfvbi.setText(dateArrv);
                /*if (destination.equalsIgnoreCase("-")&&timeDest.equalsIgnoreCase("-")) {
                    holder0.tvWalletTransportAirportArrivalfvbi.setVisibility(View.GONE);
                    holder0.tvWalletTransportTimeArrivalfvbi.setVisibility(View.GONE);
                } else {
                    holder0.tvWalletTransportAirportArrivalfvbi.setText(arrival);
                    holder0.tvWalletTransportTimeArrivalfvbi.setText(timeArrv);
                }*/
                holder0.tvWalletTransportAirportArrivalfvbi.setText(arrival);
                holder0.tvWalletTransportTimeArrivalfvbi.setText(timeArrv);

                /*Elements dds = doc.select("dd");
                for (int i = 0; i < dds.size(); i += 2) {
                    Element dd = dds.get(i);

                    String flightAirport = dd.select("h2").text();
                    String flightTime = dd.select("h3").text();
                    if (!TextUtils.isEmpty(flightAirport) && !TextUtils.isEmpty(flightTime)) {
                        if (i == 0) {
                            holder0.tvWalletTransportAirportDeparturefvbi.setText(flightAirport);
                            holder0.tvWalletTransportTimeDeparturefvbi.setText(flightTime);
                        } else if (i == 2) {
                            holder0.tvWalletTransportAirportArrivalfvbi.setText(flightAirport);
                            holder0.tvWalletTransportTimeArrivalfvbi.setText(flightTime);
                        }
                    }

                    Elements h2s = dd.select("h2");
                    for (Element h2 : h2s) {
                        String titleFlight = h2.siblingNodes().get(0).toString();
                        String flightDate = h2.siblingNodes().get(2).toString();
                        if (i == 0) {
                            holder0.tvWalletTransportTitleDeparturefvbi.setText(titleFlight);
                            holder0.tvWalletTransportDateDeparturefvbi.setText(flightDate);
                        } else if (i == 2) {
                            holder0.tvWalletTransportTitleArrivalfvbi.setText(titleFlight);
                            holder0.tvWalletTransportDateArrivalfvbi.setText(flightDate);
                        }

                    }
                }*/

                Elements dts = doc.select("dt");
                for (Element dt : dts) {
                    Elements bs = dt.select("b");
                    String flightCode = bs.text();
                    holder0.tvWalletTransportFlightCodefvbi.setText(flightCode);
                    for (Element b : bs) {
                        String flightCodeTitle = b.siblingNodes().get(0).toString();
                        holder0.tvWalletTransportFlightCodeTitlefvbi.setText(flightCodeTitle);
                    }
                }

                if (!TextUtils.isEmpty(model.getDetail())) {
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

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWalletTransportNotiffvbi, tvWalletTransportNamefvbi, tvWalletTransportTitleDeparturefvbi, tvWalletTransportAirportDeparturefvbi, tvWalletTransportDateDeparturefvbi, tvWalletTransportTimeDeparturefvbi, tvWalletTransportTitleArrivalfvbi, tvWalletTransportAirportArrivalfvbi, tvWalletTransportDateArrivalfvbi, tvWalletTransportTimeArrivalfvbi, tvWalletTransportDetailfvbi, tvWalletTransportFlightCodeTitlefvbi, tvWalletTransportFlightCodefvbi;
        private final Button bWalletTransportDetailfvbi;
        private final ImageView ivWalletTransportIconfvbi;
        SQLiteHelper db = new SQLiteHelper(itemView.getContext());

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
        SQLiteHelper db = new SQLiteHelper(itemView.getContext());

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
