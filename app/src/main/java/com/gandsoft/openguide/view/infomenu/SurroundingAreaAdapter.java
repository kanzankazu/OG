package com.gandsoft.openguide.view.infomenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNew;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNewDetail;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.github.chrisbanes.photoview.PhotoView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.util.ArrayList;

public class SurroundingAreaAdapter extends BaseExpandableListAdapter {
    private final String eventId;
    private final String accountId;
    private final SQLiteHelperMethod db;
    private ArrayList<EventSurroundingAreaNew> parentItems = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity activity;

    public SurroundingAreaAdapter(Activity activity, ArrayList<EventSurroundingAreaNew> parentItems, String eventId, String accountId) {
        this.activity = activity;
        this.parentItems = parentItems;
        this.eventId = eventId;
        this.accountId = accountId;

        db = new SQLiteHelperMethod(activity);
    }

    /**/
    /**/
    @Override
    public Object getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_surround_area_header, null);
        }

        EventSurroundingAreaNew model = (EventSurroundingAreaNew) getGroup(groupPosition);

        ImageView ivEmergencieHeaderDropdownfvbi = (ImageView) view.findViewById(R.id.ivSurroundAreaHeaderDropdown);
        TextView tvEmergencieHeaderfvbi = (TextView) view.findViewById(R.id.tvSurroundAreaHeader);

        tvEmergencieHeaderfvbi.setText(model.getTitle());
        if (isExpanded) {
            ivEmergencieHeaderDropdownfvbi.setImageResource(R.drawable.ic_drop_down);
        } else {
            ivEmergencieHeaderDropdownfvbi.setImageResource(R.drawable.ic_drop_up);
        }

        return view;
    }

    /**/
    /**/
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        /*return parentItems.get(groupPosition);*/

        return parentItems.get(groupPosition).getDetail().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_surround_area_child, null);
        }

        Log.d("Lihat", "getChildView SurroundingAreaAdapter groupPosition : " + groupPosition);
        Log.d("Lihat", "getChildView SurroundingAreaAdapter childPosition : " + childPosition);

        //WebView wvSurroundAreaChildfvbi = (WebView) view.findViewById(R.id.wvSurroundAreaChild);
        ImageView ivSurroundAreaChildfvbi = (ImageView) view.findViewById(R.id.ivSurroundAreaChild);
        TextView tvTitleSurroundAreaChildfvbi = (TextView) view.findViewById(R.id.tvTitleSurroundAreaChild);
        HtmlTextView tvDetailSurroundAreaChildfvbi = (HtmlTextView) view.findViewById(R.id.tvDetailSurroundAreaChild);
        LinearLayout llWalkMinSurroundAreaChildfvbi = (LinearLayout) view.findViewById(R.id.llWalkMinSurroundAreaChild);
        TextView tvWalkMinSurroundAreaChildfvbi = (TextView) view.findViewById(R.id.tvWalkMinSurroundAreaChild);
        LinearLayout llCarMinSurroundAreaChildfvbi = (LinearLayout) view.findViewById(R.id.llCarMinSurroundAreaChild);
        TextView tvCarMinSurroundAreaChildfvbi = (TextView) view.findViewById(R.id.tvCarMinSurroundAreaChild);
        LinearLayout llDirectionSurroundAreaChildfvbi = (LinearLayout) view.findViewById(R.id.llDirectionSurroundAreaChild);
        HtmlTextView tvDirectionSurroundAreaChildfvbi = (HtmlTextView) view.findViewById(R.id.tvDirectionSurroundAreaChild);

        EventSurroundingAreaNewDetail model = (EventSurroundingAreaNewDetail) getChild(groupPosition, childPosition);

        String stringImageIcon = AppUtil.validationStringImageIcon(activity, model.getName_image(), model.getName_image_local(), true);
        Glide.with(activity)
                .load(InputValidUtil.isLinkUrl(stringImageIcon) ? stringImageIcon : new File(stringImageIcon))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivSurroundAreaChildfvbi.setImageBitmap(resource);
                        if (NetworkUtil.isConnected(activity)) {
                            String imageCachePath = PictureUtil.saveImageLogoBackIcon(activity, resource, "SurroundArea_" + model.getTitle_image().replace(" ", "_") + eventId);
                            db.saveSurroundAreaImage(imageCachePath, accountId, eventId, model.getTitle_image());
                        }
                    }
                });

        if (TextUtils.isEmpty(model.getTitle_image())) {
            tvTitleSurroundAreaChildfvbi.setVisibility(View.GONE);
            tvTitleSurroundAreaChildfvbi.setText("");
        } else {
            tvTitleSurroundAreaChildfvbi.setVisibility(View.VISIBLE);
            tvTitleSurroundAreaChildfvbi.setText(model.getTitle_image());
        }

        if (TextUtils.isEmpty(model.getDetail_image())) {
            tvDetailSurroundAreaChildfvbi.setVisibility(View.GONE);
            tvDetailSurroundAreaChildfvbi.setHtml("");
        } else {
            tvDetailSurroundAreaChildfvbi.setVisibility(View.VISIBLE);
            tvDetailSurroundAreaChildfvbi.setHtml(model.getDetail_image());
        }

        if (TextUtils.isEmpty(model.getMinute_walk())) {
            llWalkMinSurroundAreaChildfvbi.setVisibility(View.GONE);
            tvWalkMinSurroundAreaChildfvbi.setText("");
        } else {
            llWalkMinSurroundAreaChildfvbi.setVisibility(View.VISIBLE);
            tvWalkMinSurroundAreaChildfvbi.setText(model.getMinute_walk());
        }

        if (TextUtils.isEmpty(model.getMinute_car())) {
            llCarMinSurroundAreaChildfvbi.setVisibility(View.GONE);
            tvCarMinSurroundAreaChildfvbi.setText("");
        } else {
            llCarMinSurroundAreaChildfvbi.setVisibility(View.VISIBLE);
            tvCarMinSurroundAreaChildfvbi.setText(model.getMinute_car());
        }

        if (TextUtils.isEmpty(model.getGoogle_direction())) {
            llDirectionSurroundAreaChildfvbi.setVisibility(View.GONE);
            tvDirectionSurroundAreaChildfvbi.setText("");
        } else {
            llDirectionSurroundAreaChildfvbi.setVisibility(View.VISIBLE);
            tvDirectionSurroundAreaChildfvbi.setText("Direction");
            tvDirectionSurroundAreaChildfvbi.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            tvDirectionSurroundAreaChildfvbi.setPaintFlags(tvDirectionSurroundAreaChildfvbi.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvDirectionSurroundAreaChildfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse(model.getGoogle_direction());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    activity.startActivity(mapIntent);
                }
            });
        }

        /*//child = (ArrayList<String>) childItems.get(i);
        String html = child.get(i1);
        String mimeType = "text/html";
        String encoding = "UTF-8";

        wvSurroundAreaChildfvbi.loadData(html, mimeType, encoding);
        WebSettings webSettings = wvSurroundAreaChildfvbi.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //improve performance
        webSettings.setDomStorageEnabled(true);
        //webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        wvSurroundAreaChildfvbi.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvSurroundAreaChildfvbi.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wvSurroundAreaChildfvbi.getSettings().setAppCacheEnabled(true);
        wvSurroundAreaChildfvbi.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wvSurroundAreaChildfvbi.getSettings().setLoadWithOverviewMode(true);
        wvSurroundAreaChildfvbi.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvSurroundAreaChildfvbi.setWebChromeClient(new WebChromeClient());

        *//*URLImageParser p = new URLImageParser(tvEmergencieChildfvbi, activity);
        Spanned htmlSpan = Html.fromHtml(child.get(i1), p, null);
        tvEmergencieChildfvbi.setText(htmlSpan);*/

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return ((ArrayList<String>) childItems.get(i)).size();

        //return parentItems.size();

        return parentItems.get(groupPosition).getDetail().size();
    }

    /**/
    /**/
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setInflater(LayoutInflater inflater, gSurroundingAreaActivity activity) {

        this.inflater = inflater;
        this.activity = activity;
    }

    private void customText(TextView view, String url) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("");

        spanTxt.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.colorPrimary)), 10, spanTxt.length(), 0);
        spanTxt.append("Direction");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Uri gmmIntentUri = Uri.parse(url);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                activity.startActivity(mapIntent);
            }
        }, spanTxt.length() - "Direction".length(), spanTxt.length(), 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }
}
