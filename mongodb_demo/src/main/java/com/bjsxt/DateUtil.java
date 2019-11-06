package com.bjsxt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * Date To String
     */
    public static String dateToString(String pattern, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return  simpleDateFormat.format(date);
    }

    /**
     * String To Date
     */
    public static Date stringToDate(String pattern, String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date d = null;
        try{
            d = simpleDateFormat.parse(date);
        }catch(Exception e){
            e.printStackTrace();
        }
        return d;
    }
}
