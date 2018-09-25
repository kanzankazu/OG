package com.gandsoft.openguide.support;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static String getDayFromDateString(String stringDate, SimpleDateFormat formatter) {
        //[] daysArray = new String[]{"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"};
        String[] daysArray = new String[]{"Sabtu", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat"};
        String day = "";

        int dayOfWeek = 0;
        //dateTimeFormat = yyyy-MM-dd HH:mm:ss
        //SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        Date date;
        try {
            date = formatter.parse(stringDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            if (dayOfWeek < 0) {
                dayOfWeek += 7;
            }
            day = daysArray[dayOfWeek];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day;
    }

    public static String convertStringDateToNewFormat(String timeDefault, SimpleDateFormat dateFormat, SimpleDateFormat timeFormat) {
        String mytime = timeDefault;
        //SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatDefault);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //SimpleDateFormat timeFormat = new SimpleDateFormat(dateFormatNew);
        String finalDate = timeFormat.format(myDate);

        return finalDate;
    }

    /**
     * "yyyy-MM-dd" format
     *
     * @param dateString1
     * @param dateString2
     * @return
     */
    public static List<Date> getDates(String dateString1, String dateString2, DateFormat df1) {
        ArrayList<Date> dates = new ArrayList<Date>();
        //DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /*
     * Copyright 2012 Google Inc.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }


    /**/
    public static String getTimeAgos(Date date) {

        if (date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int dim = getTimeDistanceInMinutes(time);

        String timeAgo = null;

        if (dim == 0) {
            timeAgo = "less than a minute";
        } else if (dim == 1) {
            return "1 minute";
        } else if (dim >= 2 && dim <= 44) {
            timeAgo = dim + " minutes";
        } else if (dim >= 45 && dim <= 89) {
            timeAgo = "about an hour";
        } else if (dim >= 90 && dim <= 1439) {
            timeAgo = "about " + (Math.round(dim / 60)) + " hours";
        } else if (dim >= 1440 && dim <= 2519) {
            timeAgo = "1 day";
        } else if (dim >= 2520 && dim <= 43199) {
            timeAgo = (Math.round(dim / 1440)) + " days";
        } else if (dim >= 43200 && dim <= 86399) {
            timeAgo = "about a month";
        } else if (dim >= 86400 && dim <= 525599) {
            timeAgo = (Math.round(dim / 43200)) + " months";
        } else if (dim >= 525600 && dim <= 655199) {
            timeAgo = "about a year";
        } else if (dim >= 655200 && dim <= 914399) {
            timeAgo = "over a year";
        } else if (dim >= 914400 && dim <= 1051199) {
            timeAgo = "almost 2 years";
        } else {
            timeAgo = "about " + (Math.round(dim / 525600)) + " years";
        }

        return timeAgo + " ago";
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    public static Date stringToDate(String dtStart,SimpleDateFormat format) {
        //String dtStart = "2010-10-15T09:27:37Z";
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(dtStart);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(SimpleDateFormat dateFormat) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        return dateTime;
    }
}
