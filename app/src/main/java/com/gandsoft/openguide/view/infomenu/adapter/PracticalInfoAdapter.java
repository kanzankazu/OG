package com.gandsoft.openguide.view.infomenu.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNew;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNewDetail;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class PracticalInfoAdapter extends BaseExpandableListAdapter {
    private final String accountId;
    private final String eventId;
    private Activity activity;
    SQLiteHelperMethod db = new SQLiteHelperMethod(activity);
    private LayoutInflater inflater;
    private ArrayList<EventImportanInfoNew> parentItems = new ArrayList<>();

    public PracticalInfoAdapter(Activity activity, ArrayList<EventImportanInfoNew> parents, String accountId, String eventId) {
        this.activity = activity;
        this.parentItems = parents;
        this.accountId = accountId;
        this.eventId = eventId;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_practical_info_header, null);
        }

        EventImportanInfoNew model = (EventImportanInfoNew) getGroup(groupPosition);

        ImageView ivPracticalInfoHeader = (ImageView) convertView.findViewById(R.id.ivPracticalInfoHeader);
        TextView ctvPracticalInfoHeader = (TextView) convertView.findViewById(R.id.tvPracticalInfoHeader);

        ctvPracticalInfoHeader.setText(model.getTitle());
        if (isExpanded) {
            ivPracticalInfoHeader.setImageResource(R.drawable.ic_drop_down);
        } else {
            ivPracticalInfoHeader.setImageResource(R.drawable.ic_drop_up);
        }

        return convertView;
    }

    /**/
    /**/
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        /*List<EventImportanInfoNewDetail> detail = parentItems.get(groupPosition).getDetail();
        return detail.get(childPosition);*/

        return parentItems.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_practical_info_child, null);
        }

        //child = (ArrayList<String>) childtems.get(groupPosition);
        EventImportanInfoNew model = (EventImportanInfoNew) getGroup(groupPosition);

        HtmlTextView tvPracticalInfoChild = (HtmlTextView) convertView.findViewById(R.id.tvPracticalInfoChild);
        CardView cvspPracticalInfoChild = (CardView) convertView.findViewById(R.id.cvspPracticalInfoChild);
        Spinner spPracticalInfoChild = (Spinner) convertView.findViewById(R.id.spPracticalInfoChild);
        ImageView ivPracticalInfoChild = (ImageView) convertView.findViewById(R.id.ivPracticalInfoChild);
        HtmlTextView tvDetailPracticalInfoChild = (HtmlTextView) convertView.findViewById(R.id.tvDetailPracticalInfoChild);

        ivPracticalInfoChild.setVisibility(View.GONE);
        tvDetailPracticalInfoChild.setVisibility(View.GONE);

        tvPracticalInfoChild.setHtml(model.getInfo(), new HtmlHttpImageGetter(tvPracticalInfoChild));

        Log.d("Lihat", "getChildView PracticalInfoAdapter : " + model.getDetail().size() + model.getTitle());
        Log.d("Lihat", "getChildView PracticalInfoAdapter : " + model.getDetail().get(0).getTitle_image());

        if (model.getDetail().size() <= 1) {
            cvspPracticalInfoChild.setVisibility(View.GONE);
        } else {
            cvspPracticalInfoChild.setVisibility(View.VISIBLE);

            //Spinner
            List<String> titleImageSpinner = new ArrayList<String>();
            List<String> nameImageSpinner = new ArrayList<String>();
            List<String> nameImageLocalSpinner = new ArrayList<String>();
            List<String> detailImageSpinner = new ArrayList<String>();
            titleImageSpinner.add("Select Option");
            nameImageSpinner.add("");
            nameImageLocalSpinner.add("");
            detailImageSpinner.add("");
            for (EventImportanInfoNewDetail modelDetail : model.getDetail()) {
                titleImageSpinner.add(modelDetail.getTitle_image());
                nameImageSpinner.add(modelDetail.getName_image());
                nameImageLocalSpinner.add(modelDetail.getName_image_local());
                detailImageSpinner.add(modelDetail.getDetail_image());
            }

            ArrayAdapter<String> dataAdapterimageSpinner = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, titleImageSpinner);
            dataAdapterimageSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPracticalInfoChild.setAdapter(dataAdapterimageSpinner);
            spPracticalInfoChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Log.d("Lihat", "onItemSelected PracticalInfoAdapter : " + nameImageSpinner.get(position));
                    Log.d("Lihat", "onItemSelected PracticalInfoAdapter : " + detailImageSpinner.get(position));

                    if (position > 0) {
                        ivPracticalInfoChild.setVisibility(View.VISIBLE);
                        tvDetailPracticalInfoChild.setVisibility(View.VISIBLE);
                    } else {
                        ivPracticalInfoChild.setVisibility(View.GONE);
                        tvDetailPracticalInfoChild.setVisibility(View.GONE);
                    }

                    String stringImageIcon = AppUtil.validationStringImageIcon(activity, nameImageSpinner.get(position), nameImageLocalSpinner.get(position), true);
                    Glide.with(activity)
                            .load(InputValidUtil.isLinkUrl(stringImageIcon) ? stringImageIcon : new File(stringImageIcon))
                            .asBitmap()
                            .error(R.drawable.template_account_og)
                            .placeholder(R.drawable.ic_action_name)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(false)
                            .dontAnimate()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    ivPracticalInfoChild.setImageBitmap(resource);
                                    if (NetworkUtil.isConnected(activity)) {
                                        String imageCachePath = PictureUtil.saveImageLogoBackIcon(activity, resource, "ImportantInfo" + detailImageSpinner.get(position) + eventId);
                                        db.saveImportantInfoImage(imageCachePath, accountId, eventId, accountId);
                                    }
                                }
                            });

                    tvDetailPracticalInfoChild.setHtml(detailImageSpinner.get(position), null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return ((ArrayList<String>) childtems.get(groupPosition)).size();

        //List<EventImportanInfoNewDetail> productList = parentItems.get(groupPosition).getDetail();
        //return productList.size();

        return 1;
    }

    /**/
    /**/
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
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
