package com.gandsoft.openguide.view.main.adapter;

import java.util.ArrayList;

/**
 * Authuor: Hari vignesh Jayapalan
 * Created on: 6 Feb 2016
 * Email: hariutd@gmail.com
 * <p>
 * Implementing custom RecyclerView Adapter
 * Tutorial @ https://medium.com/@harivigneshjayapalan
 */
public class PostRecViewPojo {

    private String name;
    private String time;
    private String content;
    private ArrayList<PostRecViewPojo> postRecViewPojo = new ArrayList<>();

    public PostRecViewPojo() {

    }

    public PostRecViewPojo(String name, String time, String content) {

        this.name = name;
        this.time = time;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

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
