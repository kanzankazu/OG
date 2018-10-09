package com.gandsoft.openguide.API.APIrequest;

import java.util.List;

public class PostFeedbackTheEventRequestModel {
    private String account_id;
    private String event_id;
    private String dbver;
    private List<PostFeedbackTheEventDataFeedback> data_feedback; //(isinya json contohnya [{"name":"q1","value":"4"},{"name":"q2","value":"3"},{"name":"q3","value":"3"},{"name":"q4","value":"2"},{"name":"q5","value":"1"},{"name":"q6","value":"1"},{"name":"q7","value":"pindul"},{"name":"q8","value":"bbq"},{"name":"q9","value":"appoint a committee"},{"name":"q10","value":"nope"},{"name":"event_id","value":"undefined"},{"name":"undefined","value":"undefined"}] )

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public List<PostFeedbackTheEventDataFeedback> getData_feedback() {
        return data_feedback;
    }

    public void setData_feedback(List<PostFeedbackTheEventDataFeedback> data_feedback) {
        this.data_feedback = data_feedback;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }
}
