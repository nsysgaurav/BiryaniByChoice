package com.example.test.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utility
{


    //check for internet connectivity--->
    public static boolean isOnline(Context context)
    {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;
        }

    }


//    public  static String get_date_string_format(String inputDate) throws ParseException{
//        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(inputDate);
//        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
//    }

    public static  String get_date_string_format(String date_format)
    {
        String date_string=null;

        try
        {
            //create SimpleDateFormat object with source string date format
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



            //parse the string into Date object
            Date date = sdfSource.parse(date_format);

            DateFormat destDf = new SimpleDateFormat("dd MMMM yyyy hh.mm aa");

            // format the date into another format
            date_string = destDf.format(date);






        }
        catch(ParseException pe)
        {
            System.out.println("Parse Exception : " + pe);
            date_string=date_format;
        }




        return  date_string;
    }


}
