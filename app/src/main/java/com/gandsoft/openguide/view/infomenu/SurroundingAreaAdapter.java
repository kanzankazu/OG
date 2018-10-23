package com.gandsoft.openguide.view.infomenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import java.util.ArrayList;

public class SurroundingAreaAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> headerItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private ArrayList<String> child = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity activity;

    public SurroundingAreaAdapter(Activity activity, ArrayList<String> headerItems, ArrayList<Object> childItems) {
        this.activity = activity;
        this.headerItems = headerItems;
        this.childItems = childItems;
    }

    @Override
    public int getGroupCount() {
        return headerItems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return ((ArrayList<String>) childItems.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_surround_area_header, null);
        }

        ImageView ivEmergencieHeaderDropdownfvbi = (ImageView) view.findViewById(R.id.ivSurroundAreaHeaderDropdown);
        TextView tvEmergencieHeaderfvbi = (TextView) view.findViewById(R.id.tvSurroundAreaHeader);

        tvEmergencieHeaderfvbi.setText(headerItems.get(i));
        if (b) {
            ivEmergencieHeaderDropdownfvbi.setImageResource(R.drawable.ic_drop_down);
        } else {
            ivEmergencieHeaderDropdownfvbi.setImageResource(R.drawable.ic_drop_up);
        }

        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_surround_area_child, null);
        }

        TextView tvEmergencieChildfvbi = (TextView) view.findViewById(R.id.tvSurroundAreaChild);
        WebView wvSurroundAreaChildfvbi = (WebView) view.findViewById(R.id.wvSurroundAreaChild);

        child = (ArrayList<String>) childItems.get(i);

        //String html = "<html><body>" + child.get(i1) + "</body></html>";
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

        /*URLImageParser p = new URLImageParser(tvEmergencieChildfvbi, activity);
        Spanned htmlSpan = Html.fromHtml(child.get(i1), p, null);
        tvEmergencieChildfvbi.setText(htmlSpan);*/

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void setInflater(LayoutInflater inflater, gSurroundingAreaActivity activity) {

        this.inflater = inflater;
        this.activity = activity;
    }
}
