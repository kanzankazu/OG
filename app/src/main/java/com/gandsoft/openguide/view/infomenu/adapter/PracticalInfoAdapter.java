package com.gandsoft.openguide.view.infomenu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

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

    @Override
    public int getGroupCount() {
        return parentItems.size();
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
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
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

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_practical_info_child, null);
        }

        child = (ArrayList<String>) childtems.get(groupPosition);

        HtmlTextView textView = (HtmlTextView) convertView.findViewById(R.id.tvPracticalInfoChild);

        textView.setHtml(child.get(childPosition), new HtmlHttpImageGetter(textView));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }
}
