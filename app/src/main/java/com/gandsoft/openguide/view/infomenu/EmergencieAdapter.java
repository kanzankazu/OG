package com.gandsoft.openguide.view.infomenu;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import java.util.ArrayList;

public class EmergencieAdapter extends BaseExpandableListAdapter {
    private Activity activity;

    private ArrayList<String> headerItems;
    private ArrayList<Object> childItems;
    private ArrayList<String> child = new ArrayList<>();
    private LayoutInflater inflater;

    public EmergencieAdapter(ArrayList<String> headerItems, ArrayList<Object> childItems) {
        this.headerItems = headerItems;
        this.childItems = childItems;
    }

    /**/
    /**/
    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return headerItems.size();
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_emergencie_header, null);
        }

        ImageView ivEmergencieHeaderfvbi = (ImageView) view.findViewById(R.id.ivEmergencieHeader);
        ImageView ivEmergencieHeaderDropdownfvbi = (ImageView) view.findViewById(R.id.ivEmergencieHeaderDropdown);
        TextView tvEmergencieHeaderfvbi = (TextView) view.findViewById(R.id.tvEmergencieHeader);

        tvEmergencieHeaderfvbi.setText(headerItems.get(i));
        if (b) {
            ivEmergencieHeaderDropdownfvbi.setImageResource(R.drawable.ic_drop_down);
        } else {
            ivEmergencieHeaderDropdownfvbi.setImageResource(R.drawable.ic_drop_up);
        }

        return view;
    }

    /**/
    /**/
    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_emergencie_child, null);
        }

        TextView tvEmergencieChildfvbi = (TextView) view.findViewById(R.id.tvEmergencieChild);

        child = (ArrayList<String>) childItems.get(i);

        tvEmergencieChildfvbi.setText(Html.fromHtml(child.get(i1)));

        return view;
    }

    @Override
    public int getChildrenCount(int i) {
        return ((ArrayList<String>) childItems.get(i)).size();
    }

    /**/
    /**/
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }
}
