package com.gandsoft.openguide.activity.infomenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class hFeedbackActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);
    private String accountId, eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_h_feedback);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initFeedback();
        initComponent();
        initContent();
        initListener();
    }

    private void initFeedback() {
        EventTheEvent theEvent = db.getTheEvent(eventId);
        Document doc = Jsoup.parse(theEvent.getFeedback());
        String title = doc.select("h2").text();
        Log.d("Lihat", "initFeedback hFeedbackActivity title : " + title);
        Elements subTitles = doc.select("h1");
        for (int i1 = 0; i1 < subTitles.size(); i1++) {
            Element subTitle = subTitles.get(i1);
            Log.d("Lihat", "initFeedback hFeedbackActivity subTitle.text : " + subTitle.text());

            Elements inputTypes1 = doc.select("input[name='q" + i1 + "']");
            for (int i2 = 0; i2 < inputTypes1.size(); i2++) {
                Element input = inputTypes1.get(i2);
                String name = input.attr("name");
                Log.d("Lihat", "initFeedback hFeedbackActivity name : " + name);
                String id = input.attr("id");
                Log.d("Lihat", "initFeedback hFeedbackActivity id : " + id);
                String type = input.attr("type");
                Log.d("Lihat", "initFeedback hFeedbackActivity type : " + type);
                String label = doc.select("label[for='q" + i1 + i2 + "']").text();
                Log.d("Lihat", "initFeedback hFeedbackActivity label : " + label);
            }
            Elements inputTypes2 = doc.select("textarea[name='q" + i1 + "']");
            for (int i3 = 0; i3 < inputTypes2.size(); i3++) {
                Element textarea = inputTypes2.get(i3);
                String name = textarea.attr("name");
                Log.d("Lihat", "initFeedback hFeedbackActivity name : " + name);
                String placeholder = textarea.attr("placeholder");
                Log.d("Lihat", "initFeedback hFeedbackActivity placeholder : " + placeholder);
            }
        }
    }

    private void initComponent() {

    }

    private void initContent() {

    }

    private void initListener() {

    }
}
