package com.gandsoft.openguide.activity.main.adapter;

import java.util.ArrayList;

/** Authuor: Hari vignesh Jayapalan
 *  Created on: 6 Feb 2016
 *  Email: hariutd@gmail.com
 *
 *  Implementing custom RecyclerView Adapter
 *  Tutorial @ https://medium.com/@harivigneshjayapalan
 * */
public class PostRecViewPojo {

    //POJO class consists of get method and set method
    private String name;
    private String time,content;
    private ArrayList<PostRecViewPojo> postRecViewPojo =new ArrayList<>();



    public PostRecViewPojo() {

    }
    //getting content value
    public String getContent(){return content;}
    //setting content value
    public void setContent(String content){this.content=content;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=time;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
