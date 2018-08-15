package com.gandsoft.openguide.activity.main.adapter;

import java.util.ArrayList;

public class TaskRecViewPojo {

    //POJO class consists of get method and set method
    private String name;
    private String time,content;
    private ArrayList<TaskRecViewPojo> postRecViewPojo =new ArrayList<>();

    public TaskRecViewPojo() { }

    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=time;}

}
