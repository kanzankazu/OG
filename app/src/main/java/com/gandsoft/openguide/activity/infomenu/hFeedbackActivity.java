package com.gandsoft.openguide.activity.infomenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventFeedback;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.SessionUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

public class hFeedbackActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);
    private String accountId, eventId;
    private ArrayList<EventFeedback> feedbacks = new ArrayList<>();
    private LinearLayout llfeedbackfvbi;
    private TextView tvFeedbackTitlefvbi;
    private EditText input;
    private RadioGroup rg;
    private WebView wvFeedbackfvbi;
    private Toolbar toolbar;
    private ActionBar ab;

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
        Elements subTitles = doc.select("h1");
        if (subTitles.size() > 0) {
            for (int i1 = 0; i1 < subTitles.size(); i1++) {
                EventFeedback feedback = new EventFeedback();
                ArrayList<String> subnames = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();
                String title = doc.select("h2").text();
                Element subTitle = subTitles.get(i1);
                feedback.setJudul(title);
                feedback.setSubJudul(subTitle.text());

                Elements inputs = doc.select("input[name='q" + (i1 + 1) + "']");
                Elements textareas = doc.select("textarea[name='q" + (i1 + 1) + "']");

                if (inputs.size() > 0 && textareas.size() == 0) {
                    for (int i2 = 0; i2 < inputs.size(); i2++) {
                        Element input = inputs.get(i2);
                        String type = input.attr("type");
                        String name = input.attr("name");
                        String id = input.attr("id");
                        String label = doc.select("label[for='q" + (i1 + 1) + i2 + "']").text();
                        subnames.add(id);
                        labels.add(label);
                        feedback.setInputType(type);
                        feedback.setName(name);
                    }
                    feedback.setSubName(ListArrayUtil.convertListStringToString1(subnames));
                    feedback.setLabel(ListArrayUtil.convertListStringToString1(labels));
                } else if (inputs.size() == 0 && textareas.size() > 0) {
                    for (int i3 = 0; i3 < textareas.size(); i3++) {
                        Element textarea = textareas.get(i3);
                        String name = textarea.attr("name");
                        String placeholder = textarea.attr("placeholder");
                        feedback.setInputType("textarea");
                        feedback.setName(name);
                        feedback.setPlaceholder(placeholder);
                    }
                }

                //db.saveFeedback(feedback, eventId);
                feedbacks.add(feedback);
            }
        } else {
            EventFeedback feedback = new EventFeedback();
            String title = doc.select("h2").text();
            feedback.setJudul(title);
            feedbacks.add(feedback);
        }


        /*if (db.isDataTableValueNull(SQLiteHelper.TableFeedback, SQLiteHelper.Key_Feedback_EventId, eventId)) {
            Log.d("Lihat", "initFeedback hFeedbackActivity : " + "feedback sqlite kosong");
            EventTheEvent theEvent = db.getTheEvent(eventId);
            ArrayList<String> subnames = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            Document doc = Jsoup.parse(theEvent.getFeedback());
            Elements subTitles = doc.select("h1");
            for (int i1 = 0; i1 < subTitles.size(); i1++) {
                EventFeedback feedback = new EventFeedback();
                String title = doc.select("h2").text();
                Element subTitle = subTitles.get(i1);
                Log.d("Lihat", "initFeedback hFeedbackActivity title : " + title);
                Log.d("Lihat", "initFeedback hFeedbackActivity subTitle.text : " + subTitle.text());
                feedback.setJudul(title);
                feedback.setSubJudul(subTitle.text());

                Elements inputTypes1 = doc.select("input[name='q" + i1 + "']");
                for (int i2 = 0; i2 < inputTypes1.size(); i2++) {
                    Element input = inputTypes1.get(i2);
                    String type = input.attr("type");
                    String name = input.attr("name");
                    String id = input.attr("id");
                    String label = doc.select("label[for='q" + i1 + i2 + "']").text();
                    Log.d("Lihat", "initFeedback hFeedbackActivity type : " + type);
                    Log.d("Lihat", "initFeedback hFeedbackActivity name : " + name);
                    Log.d("Lihat", "initFeedback hFeedbackActivity subname : " + id);
                    Log.d("Lihat", "initFeedback hFeedbackActivity label : " + label);
                    feedback.setInputType(type);
                    feedback.setName(name);
                    subnames.add(id);
                    labels.add(label);
                }
                feedback.setSubName(ListArrayUtil.convertListStringToString1(subnames));
                feedback.setLabel(ListArrayUtil.convertListStringToString1(labels));

                Elements inputTypes2 = doc.select("textarea[name='q" + i1 + "']");
                for (int i3 = 0; i3 < inputTypes2.size(); i3++) {
                    Element textarea = inputTypes2.get(i3);
                    String name = textarea.attr("name");
                    String placeholder = textarea.attr("placeholder");
                    Log.d("Lihat", "initFeedback hFeedbackActivity name : " + name);
                    Log.d("Lihat", "initFeedback hFeedbackActivity placeholder : " + placeholder);
                    feedback.setInputType("textarea");
                    feedback.setName(name);
                    feedback.setPlaceholder(placeholder);
                }
                db.saveFeedback(feedback, eventId);
                feedbacks.add(feedback);
                Log.d("Lihat", "initFeedback hFeedbackActivity 92 : " + feedbacks);
            }
        } else {
            feedbacks = db.getFeedback(eventId);
            Log.d("Lihat", "initFeedback hFeedbackActivity : " + "feedback sqlite ada");
            Log.d("Lihat", "initFeedback hFeedbackActivity : " + feedbacks.size());
        }*/

    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        llfeedbackfvbi = (LinearLayout) findViewById(R.id.llfeedback);
        tvFeedbackTitlefvbi = (TextView) findViewById(R.id.tvFeedbackTitle);
    }

    @SuppressLint("SetTextI18n")
    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Feedback");

        uiFeedback();

    }

    private void initListener() {

    }

    private void uiFeedback() {
        if (feedbacks.size() > 1) {
            String judul = feedbacks.get(0).getJudul();
            tvFeedbackTitlefvbi.setText(judul);

            for (int i1 = 0; i1 < feedbacks.size(); i1++) {
                EventFeedback feedback = feedbacks.get(i1);
                String subJudul = feedback.getSubJudul();
                String inputType = feedback.getInputType();
                String name = feedback.getName();
                String subName = feedback.getSubName();
                String label = feedback.getLabel();
                String placeholder = feedback.getPlaceholder();

                int paddingDp = 10;
                float density = getApplicationContext().getResources().getDisplayMetrics().density;
                int paddingPixel = (int) (paddingDp * density);

                View line = new View(this);
                line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                line.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                llfeedbackfvbi.addView(line);

                LinearLayout childLL = new LinearLayout(this);
                LinearLayout.LayoutParams paramChildLL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                childLL.setLayoutParams(paramChildLL);
                childLL.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                childLL.setOrientation(LinearLayout.VERTICAL);

                TextView tvSubJudul = new TextView(this);
                tvSubJudul.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tvSubJudul.setText(subJudul);
                tvSubJudul.setTextSize(17);
                tvSubJudul.setTextColor(getResources().getColor(R.color.colorAccent));
                childLL.addView(tvSubJudul);

                if (inputType.equalsIgnoreCase("checkbox")) {

                    String[] labels = ListArrayUtil.convertStringToArrayString1(feedback.getLabel());
                    String[] subNames = ListArrayUtil.convertStringToArrayString1(feedback.getSubName());
                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    for (int i = 0; i < labels.length; i++) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setId(i);
                        checkBox.setText(labels[i]);
                        row.addView(checkBox);
                    }
                    childLL.addView(row);

                } else if (inputType.equalsIgnoreCase("radio")) {

                    String[] labels = ListArrayUtil.convertStringToArrayString1(feedback.getLabel());
                    String[] subNames = ListArrayUtil.convertStringToArrayString1(feedback.getSubName());
                    rg = new RadioGroup(this); //create the RadioGroup
                    rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
                    for (int i = 0; i < labels.length; i++) {
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setText(labels[i]);
                        radioButton.setId(i1 + i);
                        rg.addView(radioButton);
                    }
                    childLL.addView(rg);

                } else if (inputType.equalsIgnoreCase("textarea")) {

                    input = new EditText(this);
                    input.setHint(feedback.getPlaceholder());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    childLL.addView(input);

                }
                llfeedbackfvbi.addView(childLL);
            }

            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setBackgroundResource(R.drawable.background_radius_bottom);
            button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            button.setGravity(Gravity.CENTER);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setText("SEND");
            llfeedbackfvbi.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            String judul = feedbacks.get(0).getJudul();
            tvFeedbackTitlefvbi.setText(judul);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
