package com.gandsoft.openguide.activity.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;


public class dAboutFragment extends Fragment {
	private String accountId, eventId;
	private int version_data_event;
	SQLiteHelper db;

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

	private void initComponent(){
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
                tvAboutBodyTextfvbi.setText(Html.fromHtml(HTMLPart(model.getDescription(),"body")));
                tvAboutFooterTextfvbi.setText(HTMLPart(model.getDescription(),"title"));
                Glide.with(getActivity().getApplicationContext())
                        .load(model.getBackground())
                        .placeholder(R.drawable.template_account_og)
                        .error(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ivAboutBackgroundfvbi);
                Glide.with(getActivity().getApplicationContext())
                        .load(model.getLogo())
                        .placeholder(R.drawable.template_account_og)
                        .error(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ivAboutLogofvbi);
            }
        }
    }

    public String HTMLPart(String html, String a){
        String[] splitHtml = html.split("<small>");
        String content;
        if(a.equals("title")){
            content = splitHtml[1].replaceAll("</small>","");
        }
        else{
            content = splitHtml[0];
        }
        return content;
    }

}
