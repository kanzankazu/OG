package com.gandsoft.openguide.view.infomenu.adapter;

import java.util.ArrayList;

/**
 * Authuor: Hari vignesh Jayapalan
 * Created on: 6 Feb 2016
 * Email: hariutd@gmail.com
 * <p>
 * Implementing custom RecyclerView Adapter
 * Tutorial @ https://medium.com/@harivigneshjayapalan
 */
public class InboxRecViewPojo {

    //POJO class consists of get method and set method
    private String name;
    private String time, content;
    private ArrayList<InboxRecViewPojo> inboxRecViewPojo = new ArrayList<>();

    public InboxRecViewPojo() {

    }

    //getting content value
    public String getContent() {
        return content;
    }

    //setting content value
    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
