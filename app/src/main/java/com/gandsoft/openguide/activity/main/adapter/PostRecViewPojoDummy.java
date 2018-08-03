package com.gandsoft.openguide.activity.main.adapter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PostRecViewPojoDummy {
    private static final int COUNT = 10;
    private static final int TOTALPAGE = 5;//5+1*10

    public static List<PostRecViewPojo> generyData(int page) {
        Log.d("Lihat generyData PostRecViewPojoDummy page", String.valueOf(page));
        int start = page * COUNT;//0
        Log.d("Lihat generyData PostRecViewPojoDummy start", String.valueOf(start));
        int end = TOTALPAGE == page ? start + COUNT : start + COUNT;
        Log.d("Lihat generyData PostRecViewPojoDummy end", String.valueOf(end));
        List<PostRecViewPojo> items = new ArrayList<PostRecViewPojo>();
        for (int i = start; i < end; i++) {
            //Log.d("Lihat generyData PostRecViewPojoDummy i", String.valueOf(i));
            items.add(createDummyItem(i));
        }
        return items;
    }

    public static boolean hasMore(int page) {
        return page < TOTALPAGE;
    }

    private static PostRecViewPojo createDummyItem(int position) {
        return new PostRecViewPojo("User Name", "Content, number: " + position, "Time");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        int count = position % 3;
        for (int i = 0; i < count; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
