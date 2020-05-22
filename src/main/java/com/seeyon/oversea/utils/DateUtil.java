package com.seeyon.oversea.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static String format(Date date,String regex)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(regex);

        String dateString = sdf.format(date);

        return dateString;
    }


    public static Date parse(String stringDate,String regex) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(regex);

        Date date = sdf.parse(stringDate);

        return date;
    }
}
