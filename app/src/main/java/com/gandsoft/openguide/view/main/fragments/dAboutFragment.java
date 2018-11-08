package com.gandsoft.openguide.view.main.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.io.File;
import java.util.ArrayList;

public class dAboutFragment extends Fragment {
    SQLiteHelper db;
    private String accountId, eventId;
    private int version_data_event;
    private View view;
    private ImageView ivAboutBackgroundfvbi;
    private ImageView ivAboutLogofvbi;
    private TextView tvAboutBodyTextfvbi;
    private TextView tvAboutFooterTextfvbi;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_d_about, container, false);
        db = new SQLiteHelper(getActivity());

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent();
        initContent();
        return view;
    }

    private void initComponent() {
        ivAboutBackgroundfvbi = (ImageView) view.findViewById(R.id.ivAboutBackground);
        ivAboutLogofvbi = (ImageView) view.findViewById(R.id.ivAboutLogo);
        tvAboutBodyTextfvbi = (TextView) view.findViewById(R.id.tvAboutBodyText);
        tvAboutFooterTextfvbi = (TextView) view.findViewById(R.id.tvAboutFooter);
    }

    private void initContent() {
        updateUi();
    }

    private void updateUi() {
        ArrayList<EventAbout> models = db.getAbout(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                EventAbout model = models.get(i);
                tvAboutBodyTextfvbi.setText(Html.fromHtml(HTMLPart(model.getDescription(), "body")));
                tvAboutFooterTextfvbi.setText(HTMLPart(model.getDescription(), "title"));
            }
        }

        UserListEventResponseModel oneListEvent = db.getOneListEvent(eventId, accountId);
        String logo;
        String background;
        if (NetworkUtil.isConnected(getActivity())) {
            logo = oneListEvent.getLogo();
            background = oneListEvent.getBackground();
        } else {
            logo = oneListEvent.getLogo_local();
            background = oneListEvent.getBackground_local();

        }
        Glide.with(getActivity())
                .load(InputValidUtil.isLinkUrl(logo) ? logo : new File(logo))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivAboutLogofvbi.setImageBitmap(resource);
                        if (NetworkUtil.isConnected(getActivity())) {
                            String imageCachePath = PictureUtil.saveImageLogoBackIcon(getActivity(), resource, eventId + "_Logo_image");
                            db.saveEventLogoPicture(imageCachePath, accountId, eventId);
                        }
                    }
                });
        Glide.with(getActivity())
                .load(InputValidUtil.isLinkUrl(background) ? background : new File(background))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivAboutBackgroundfvbi.setImageBitmap(resource);
                        if (NetworkUtil.isConnected(getActivity())) {
                            String imageCachePath = PictureUtil.saveImageLogoBackIcon(getActivity(), resource, eventId + "_Background_image");
                            db.saveEventBackgroundPicture(imageCachePath, accountId, eventId);
                        }
                    }
                });
    }

    public String HTMLPart(String html, String a) {
        String[] splitHtml = html.split("<small>");
        String content;
        if (a.equals("title")) {
            content = splitHtml[1].replaceAll("</small>", "");
        } else {
            content = splitHtml[0];
        }
        return content;
    }

}
