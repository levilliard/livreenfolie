package com.mbds.livreenfolie.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GlobalVar {
    public static int TAB_INDEX = 0;
    public static String SEARCH_CRITERIA = "";


    public static String getNewsName(String type) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());
        String[] temp = currentDateandTime.split("/");
        return type + temp[0] + "-" + temp[1];

    }

    public static String getPublishedDate(String date) {

        String publishDate = date.replace("T", "'T'").replace("Z", "'Z'");

        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getDefault());

            cal1.setTime(sdf.parse(date));
            cal1.add(Calendar.HOUR_OF_DAY, -5);

            //Log.w("warning", "news date = " + cal1.getTime().toString());
            //Log.w("warning", "now date = " + cal2.getTime().toString());

            int diff = cal2.get(Calendar.DAY_OF_MONTH) - cal1.get(Calendar.DAY_OF_MONTH);
            //Log.w("warning", "diff date = " + diff);

            if (diff == 0) {
                publishDate = "Aujourd'hui";
            } else if (diff == 1) {
                publishDate = "Hier";
            } else if (diff == 2) {
                publishDate = "Avant-Hier";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return publishDate.split("'T'")[0];
    }

    public static Date toLocalDate(String date) {
        Calendar cal1 = Calendar.getInstance();
        Date temp = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getDefault());
        try {
            temp = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static String formatHour(double hour) {
        String temp = "";

        if (hour > 12) {
            int hr = ((int) hour) % 12;
            int tp = ((int) (100 * hour) - 100 * ((int) hour));
            if (tp == 0) {
                temp = hr + ":" + "00" + " PM";

            } else {
                temp = hr + ":" + tp + " PM";
            }
        } else {
            int tp = (int) ((100 * hour - 100 * (int) hour));
            if (tp == 0) {
                temp = (int) hour + ":" + "00" + " AM";
            } else {
                temp = (int) hour + ":" + tp + " AM";
            }
        }

        temp.replace(":0 ", ":00 ");
        return temp;
    }
}
