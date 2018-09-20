package com.gandsoft.openguide.API.APIrequest.HomeContent;

public class HomeContentPostCommentSetRequestModel {
    String event_id;
    String post_id;
    String account_id;
    String post_comment;
    String post_time;
    String timezone;
    String dbver;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getPost_comment() {
        return post_comment;
    }

    public void setPost_comment(String post_comment) {
        this.post_comment = post_comment;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDbver() {
        return dbver;
    }

    public void setDbver(String dbver) {
        this.dbver = dbver;
    }
}
