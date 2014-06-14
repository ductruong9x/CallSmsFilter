package com.truongtvd.callsmsfilter.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by truongtvd on 6/12/14.
 */
public class Util {

    public static String convertDate(int time) {
        String date_convert = null;
        Date date = new Date(time * (long) 1000);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        date_convert = format.format(date).toString();
        return date_convert;
    }
}
