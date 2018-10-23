package com.gandsoft.openguide.view.infomenu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.PostFeedbackTheEventDataFeedback;
import com.gandsoft.openguide.API.APIrequest.PostFeedbackTheEventRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventFeedback;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.PostFeedbackTheEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.SessionUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (subTitles.size() > 0) { // get subtitle
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

                //db.saveFeedback(feedback, event_Id);
                feedbacks.add(feedback);
            }
        } else {
            EventFeedback feedback = new EventFeedback();
            String title = doc.select("h2").text();
            feedback.setJudul(title);
            feedbacks.add(feedback);
        }
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
        if (!isHasFeedback()) {
            if (isOutDateEvent()) {
                if (isHasntStartDateEvent()) {
                    tvFeedbackTitlefvbi.setText("Thank you for participating in our new_event. but sorry you can't give feedback because, the new_event hasn't started yet. see you on the new_event");
                } else if (isHasPassedDateEvent()) {
                    tvFeedbackTitlefvbi.setText("Thank you for participating in our new_event. but sorry you can't give feedback because, You have passed the new_event. see you on the next new_event");
                }
            } else {
                if (feedbacks.size() > 1) {
                    setFeedbackForm();
                } else {
                    String judul = feedbacks.get(0).getJudul();
                    tvFeedbackTitlefvbi.setText(judul);
                }
            }
        } else {
            tvFeedbackTitlefvbi.setText("Thanks you for the feedback. Your feedback is very important to us. see you on the next new_event");
        }
    }

    private void setFeedbackForm() {
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
                    checkBox.setTag(subNames[i]);
                    checkBox.setText(labels[i]);
                    row.addView(checkBox);
                }
                childLL.addView(row);

            } else if (inputType.equalsIgnoreCase("radio")) {

                String[] labels = ListArrayUtil.convertStringToArrayString1(feedback.getLabel());
                String[] subNames = ListArrayUtil.convertStringToArrayString1(feedback.getSubName());
                rg = new RadioGroup(this); //create the RadioGroup
                rg.setId(i1);
                rg.setTag(name);
                rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
                for (int i = 0; i < labels.length; i++) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setId(i);
                    radioButton.setTag(subNames[i]);
                    radioButton.setText(labels[i]);
                    rg.addView(radioButton);
                }
                childLL.addView(rg);

            } else if (inputType.equalsIgnoreCase("textarea")) {

                input = new EditText(this);
                input.setId(i1);
                input.setTag(name);
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
                Snackbar.make(findViewById(android.R.id.content), "clickked", Snackbar.LENGTH_SHORT).show();

                List<PostFeedbackTheEventDataFeedback> models = new ArrayList<>();

                for (int i = 0; i < llfeedbackfvbi.getChildCount(); i++) {
                    View child = llfeedbackfvbi.getChildAt(i);


                    if (child instanceof LinearLayout) {
                        LinearLayout linearLayout = (LinearLayout) child;

                        for (int i1 = 0; i1 < linearLayout.getChildCount(); i1++) {
                            View child1 = linearLayout.getChildAt(i1);

                            if (child1 instanceof RadioGroup) {
                                PostFeedbackTheEventDataFeedback model = new PostFeedbackTheEventDataFeedback();

                                RadioGroup radioGroup = (RadioGroup) child1;
                                Log.d("Lihat", "onClick hFeedbackActivity radioGroup.getTag: " + radioGroup.getTag());
                                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                                for (int i2 = 0; i2 < radioGroup.getChildCount(); i2++) {
                                    RadioButton btn = (RadioButton) radioGroup.getChildAt(i2);
                                    if (btn.getId() == checkedRadioButtonId) {
                                        model.setName(radioGroup.getTag().toString());
                                        model.setValue(btn.getTag().toString().substring(2));
                                        models.add(model);
                                        Log.d("Lihat", "onClick hFeedbackActivity radioButton.getTag: " + btn.getTag().toString().substring(2));
                                    }
                                }
                            } else if (child1 instanceof EditText) {
                                PostFeedbackTheEventDataFeedback model = new PostFeedbackTheEventDataFeedback();

                                EditText editText = (EditText) child1;
                                model.setName(editText.getTag().toString());
                                model.setValue(editText.getText().toString().trim());
                                models.add(model);
                                Log.d("Lihat", "onClick hFeedbackActivity editText.getTag().toString(): " + editText.getTag().toString());
                                Log.d("Lihat", "onClick hFeedbackActivity editText.getText().toString(): " + editText.getText().toString().trim());
                            } /*else if (child1 instanceof CheckBox) {

                            }*/
                        }
                    }

                    if (i == (llfeedbackfvbi.getChildCount() - 1)) {
                        ProgressDialog progressDialog = ProgressDialog.show(hFeedbackActivity.this, "Loading...", "Please Wait..", false, false);

                        PostFeedbackTheEventRequestModel requestModel = new PostFeedbackTheEventRequestModel();
                        requestModel.setAccount_id(accountId);
                        requestModel.setEvent_id(eventId);
                        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
                        requestModel.setData_feedback(models);

                        API.doPostFeedbackTheEventRet(requestModel).enqueue(new Callback<List<PostFeedbackTheEventResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<PostFeedbackTheEventResponseModel>> call, Response<List<PostFeedbackTheEventResponseModel>> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    List<PostFeedbackTheEventResponseModel> models = response.body();
                                    if (models.get(0).getStatus().equalsIgnoreCase("ok")) {
                                        finish();
                                        Snackbar.make(findViewById(android.R.id.content), "Success give feedback", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        Snackbar.make(findViewById(android.R.id.content), "Failed give feedback", Snackbar.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d("Lihat", "onFailure hFeedbackActivity : " + response.message());
                                    //Crashlytics.logException(new Exception(response.message()));
                                    Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PostFeedbackTheEventResponseModel>> call, Throwable t) {
                                progressDialog.dismiss();
                                //Crashlytics.logException(new Exception(t.getMessage()));
                                Log.d("Lihat", "onFailure hFeedbackActivity : " + t.getMessage());
                                Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean isHasFeedback() {

        UserWalletDataResponseModel walletData = db.getWalletDataIdCard(eventId);
        String replace1 = walletData.getBody_wallet().replace("\\", " ");
        String replace2 = replace1.replace("\"", " ");
        String replace3 = replace2.replace("status-checkin , ", "status-checkin,");
        String replace4 = replace3.replace("status-feedback , ", "status-feedback,");

        /*EXECUTE*/
        return replace4.matches("(?i).*status-feedback,1.*");
    }

    public boolean isHasntStartDateEvent() {
        /*INIT VALIDATE DATE*/
        Date currentDate = DateTimeUtil.currentDate();

        String startDate = db.getOneListEvent(eventId,accountId).getStart_date();//"yyyyMMdd"
        String startDateYearMonth = startDate.substring(0, 6);//"yyyyMM"

        String dateStartEnd = db.getOneListEvent(eventId, accountId).getDate();//"dd-dd mm yyyy"
        String[] dates = dateStartEnd.split(" ");
        String daysEvent = dates[0];
        String[] daysEvents = daysEvent.split("-");
        String startDay = daysEvents[0];
        String startDay1 = startDay.length() == 1 ? "0" + startDay : startDay;
        String endDay = daysEvents[1];
        String endDay1 = endDay.length() == 1 ? "0" + endDay : endDay;

        String startDayComplete = startDateYearMonth + startDay1 + " 00:00:00";//"yyyyMMdd HH:mm:ss"
        Date startDayCompleteD = DateTimeUtil.stringToDate(startDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));
        String endDayComplete = startDateYearMonth + endDay1 + " 23:59:59";//"yyyyMMdd HH:mm:ss"
        Date endDayCompleteD = DateTimeUtil.stringToDate(endDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));

        Date day3BeforeStart = DateTimeUtil.addDays(startDayCompleteD, -3);
        Date day7AfterEnd = DateTimeUtil.addDays(endDayCompleteD, 7);

        return DateTimeUtil.isNowBeforeDate(startDayCompleteD);
    }

    public boolean isHasPassedDateEvent() {
        /*INIT VALIDATE DATE*/
        Date currentDate = DateTimeUtil.currentDate();

        String startDate = db.getOneListEvent(eventId, accountId).getStart_date();//"yyyyMMdd"
        String startDateYearMonth = startDate.substring(0, 6);//"yyyyMM"

        String dateStartEnd = db.getOneListEvent(eventId, accountId).getDate();//"dd-dd mm yyyy"
        String[] dates = dateStartEnd.split(" ");
        String daysEvent = dates[0];
        String[] daysEvents = daysEvent.split("-");
        String startDay = daysEvents[0];
        String startDay1 = startDay.length() == 1 ? "0" + startDay : startDay;
        String endDay = daysEvents[1];
        String endDay1 = endDay.length() == 1 ? "0" + endDay : endDay;

        String startDayComplete = startDateYearMonth + startDay1 + " 00:00:00";//"yyyyMMdd HH:mm:ss"
        Date startDayCompleteD = DateTimeUtil.stringToDate(startDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));
        String endDayComplete = startDateYearMonth + endDay1 + " 23:59:59";//"yyyyMMdd HH:mm:ss"
        Date endDayCompleteD = DateTimeUtil.stringToDate(endDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));

        Date day3BeforeStart = DateTimeUtil.addDays(startDayCompleteD, -3);
        Date day7AfterEnd = DateTimeUtil.addDays(endDayCompleteD, 7);

        /*EXECUTE*/
        return DateTimeUtil.isNowAfterDate(day7AfterEnd);
    }

    public boolean isOutDateEvent() {

        /*INIT VALIDATE DATE*/
        Date currentDate = DateTimeUtil.currentDate();

        String startDate = db.getOneListEvent(eventId, accountId).getStart_date();//"yyyyMMdd"
        String startDateYearMonth = startDate.substring(0, 6);//"yyyyMM"

        String dateStartEnd = db.getOneListEvent(eventId, accountId).getDate();//"dd-dd mm yyyy"
        String[] dates = dateStartEnd.split(" ");
        String daysEvent = dates[0];
        String[] daysEvents = daysEvent.split("-");
        String startDay = daysEvents[0];
        String startDay1 = startDay.length() == 1 ? "0" + startDay : startDay;
        String endDay = daysEvents[1];
        String endDay1 = endDay.length() == 1 ? "0" + endDay : endDay;

        String startDayComplete = startDateYearMonth + startDay1 + " 00:00:00";//"yyyyMMdd HH:mm:ss"
        Date startDayCompleteD = DateTimeUtil.stringToDate(startDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));
        String endDayComplete = startDateYearMonth + endDay1 + " 23:59:59";//"yyyyMMdd HH:mm:ss"
        Date endDayCompleteD = DateTimeUtil.stringToDate(endDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));

        Date day3BeforeStart = DateTimeUtil.addDays(startDayCompleteD, -3);
        Date day7AfterEnd = DateTimeUtil.addDays(endDayCompleteD, 7);

        /*EXECUTE*/
        return !DateTimeUtil.isBetween2Date(startDayCompleteD, day7AfterEnd);
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
