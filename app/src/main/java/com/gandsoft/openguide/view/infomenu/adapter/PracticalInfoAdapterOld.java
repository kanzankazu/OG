package com.gandsoft.openguide.view.infomenu.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.model.BaseExpandableListModel;
import com.gandsoft.openguide.model.SubBaseExpanderListModel;
import com.gandsoft.openguide.support.SystemUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class PracticalInfoAdapterOld extends BaseExpandableListAdapter {
    private Activity activity;

    private List<BaseExpandableListModel> headerItems = new ArrayList<>();
    private LayoutInflater inflater;

    String regexSpan = "<span>(.+?)</span>";
    String regexImg = "<img src=(.+?)>";

    public PracticalInfoAdapterOld(Activity activity, List<BaseExpandableListModel> headerItems) {
        this.activity = activity;
        this.headerItems = headerItems;
    }

    /**/
    /**/
    @Override
    public Object getGroup(int i) {
        return headerItems.get(i);
    }

    @Override
    public int getGroupCount() {
        return headerItems.size();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_emergencie_header, null);
        }

        BaseExpandableListModel model = (BaseExpandableListModel) getGroup(i);
        String title = model.getTitle();

        ImageView ivEmergencieHeaderfvbi = (ImageView) view.findViewById(R.id.ivEmergencieHeader);
        ImageView ivEmergencieHeaderDropdownfvbi = (ImageView) view.findViewById(R.id.ivEmergencieHeaderDropdown);
        TextView tvEmergencieHeaderfvbi = (TextView) view.findViewById(R.id.tvEmergencieHeader);

        tvEmergencieHeaderfvbi.setText(title);
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
        return headerItems.get(i).getDetail().get(i1);
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        Log.d("Lihat", "getChildView PracticalInfoAdapterOld : " + headerItems.size());
        Log.d("Lihat", "getChildView PracticalInfoAdapterOld i: " + i);
        Log.d("Lihat", "getChildView PracticalInfoAdapterOld i1: " + i1);
        Log.d("Lihat", "getChildView PracticalInfoAdapterOld getChildrenCount: " + getChildrenCount(i));
        Log.d("Lihat", "getChildView PracticalInfoAdapterOld getGroupCount: " + getGroupCount());

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_practical_info_child_old, null);
        }

        /**/
        LinearLayout llPracticalInfoOldTVfvbi = (LinearLayout) view.findViewById(R.id.llPracticalInfoOldTV);
        TextView tvPracticalInfoChildfvbi = (TextView) view.findViewById(R.id.tvPracticalInfoOldTitle);
        LinearLayout llPracticalInfoOldMainfvbi = (LinearLayout) view.findViewById(R.id.llPracticalInfoOldMain);
        llPracticalInfoOldMainfvbi.removeAllViewsInLayout();

        /**/
        SubBaseExpanderListModel model = (SubBaseExpanderListModel) getChild(i, i1);
        String s = model.getInfo();
        Log.d("Lihat", "getChildView PracticalInfoAdapterOld s: " + s);
        /**/
        List<String> tagSpan = SystemUtil.getTagValues(s, regexSpan);
        List<String> tagImg = SystemUtil.getTagValues(s, regexImg);

        String s1 = s.replaceAll(regexImg, "<a>");
        String s2 = s1.replaceAll(regexSpan, "=#=");
        String s3 = s2.replaceAll("\"", "");
        String s4 = s3.replaceAll("<br><br>", "");
        String s5 = s4.replaceAll("<br><br><br>", "");
        String[] split = s5.split("=#=");
        Log.d("Lihat", "onClick ChangeEventActivity split.length : " + split.length);
        Log.d("Lihat", "getChildView PracticalInfoAdapterOld tagSpan.size : " + tagSpan.size());

        if (tagSpan.size() == 0) {
            llPracticalInfoOldTVfvbi.setVisibility(View.VISIBLE);
            llPracticalInfoOldMainfvbi.setVisibility(View.GONE);
            tvPracticalInfoChildfvbi.setText(Html.fromHtml(s));
        } else {
            llPracticalInfoOldTVfvbi.setVisibility(View.GONE);
            llPracticalInfoOldMainfvbi.setVisibility(View.VISIBLE);
        }

        int paddingDp = 10;
        float density = activity.getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * density);

        LinearLayout childLL = new LinearLayout(activity);
        LinearLayout.LayoutParams paramChildLL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childLL.setLayoutParams(paramChildLL);
        childLL.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
        childLL.setOrientation(LinearLayout.VERTICAL);

        for (int iq = 0; iq < split.length; iq++) {
            String sHtmlInfo = split[iq];

            HtmlTextView htvInfo = new HtmlTextView(activity);
            htvInfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            htvInfo.setHtml(sHtmlInfo);
            htvInfo.setTextSize(15);
            setMargins(htvInfo, 0, 10, 0, 10);
            childLL.addView(htvInfo);

            if (iq < tagSpan.size() && iq < tagImg.size()) {
                String sTitleImageInfo = tagSpan.get(iq);
                String sImageInfo = tagImg.get(iq);

                CardView cvButton = new CardView(activity);
                cvButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                cvButton.setCardBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                cvButton.setRadius(20);

                LinearLayout llImageShow = new LinearLayout(activity);
                LinearLayout.LayoutParams paramChildLLButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                llImageShow.setLayoutParams(paramChildLLButton);
                llImageShow.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                llImageShow.setOrientation(LinearLayout.HORIZONTAL);

                TextView tvImageTitle = new TextView(activity);
                tvImageTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                tvImageTitle.setGravity(Gravity.CENTER);
                tvImageTitle.setText(sTitleImageInfo);
                tvImageTitle.setTextColor(activity.getResources().getColor(R.color.white));
                tvImageTitle.setTextSize(15);
                llImageShow.addView(tvImageTitle);

                ImageView ivIcon = new ImageView(activity);
                ivIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                setMargins(ivIcon, 10, 0, 10, 0);
                ivIcon.setImageResource(R.drawable.ic_drop_down);
                ivIcon.setColorFilter(ContextCompat.getColor(activity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                llImageShow.addView(ivIcon);

                cvButton.addView(llImageShow);
                childLL.addView(cvButton);

                ImageView ivInfo = new ImageView(activity);
                ivInfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                setMargins(ivInfo, 0, 10, 0, 0);
                ivInfo.setVisibility(View.GONE);

                Glide.with(activity)
                        .load(sImageInfo)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.template_account_og)
                        .into(ivInfo);
                childLL.addView(ivInfo);

                cvButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ivInfo.getVisibility() == View.GONE) {
                            ivInfo.setVisibility(View.VISIBLE);
                            ivIcon.setImageResource(R.drawable.ic_drop_up);
                        } else {
                            ivInfo.setVisibility(View.GONE);
                            ivIcon.setImageResource(R.drawable.ic_drop_down);
                        }
                    }
                });
            }
        }
        llPracticalInfoOldMainfvbi.addView(childLL);

        return view;
    }

    @Override
    public int getChildrenCount(int i) {
        //return headerItems.get(i).getDetail().size();
        return 1;
    }

    /**/
    /**/
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
