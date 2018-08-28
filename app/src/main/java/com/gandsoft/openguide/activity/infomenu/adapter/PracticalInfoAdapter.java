package com.gandsoft.openguide.activity.infomenu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gandsoft.openguide.R;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class PracticalInfoAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childtems = new ArrayList<>();
    private ArrayList<String> child = new ArrayList<>();

    public PracticalInfoAdapter(ArrayList<String> parents, ArrayList<Object> childern) {
        this.parentItems = parents;
        this.childtems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_practical_info_header, null);
        }

        ImageView ivPracticalInfoHeader = (ImageView) convertView.findViewById(R.id.ivPracticalInfoHeader);
        TextView ctvPracticalInfoHeader = (TextView) convertView.findViewById(R.id.ctvPracticalInfoHeader);

        ctvPracticalInfoHeader.setText(parentItems.get(groupPosition));
        if (isExpanded) {
            ivPracticalInfoHeader.setImageResource(R.drawable.ic_drop_down);
        } else {
            ivPracticalInfoHeader.setImageResource(R.drawable.ic_drop_up);

        }

        /*((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);*/

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_practical_info_child, null);
        }

        textView = (TextView) convertView.findViewById(R.id.textView1);

        textView.setText(child.get(childPosition));
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(activity, child.get(childPosition) + " holaaa",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
