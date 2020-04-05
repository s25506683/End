package com.example.demo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTimeStamp {

    private CurrentTimeStamp currenttimestamp;
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Timestamp gettimestamp = new Timestamp(System.currentTimeMillis());
    
    String timestamp = df.format(gettimestamp);

    public String getCurrentTimeStamp(){
        return timestamp;
    }

        // //method 1
        // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // System.out.println(timestamp);
        //output will like "2016-11-16 06:43:19.77"

        // //method 2 - via Date
        // Date date = new Date();
        // System.out.println(new Timestamp(date.getTime()));
        //output will like "2016-11-16 06:43:19.769"

        // //return number of milliseconds since January 1, 1970, 00:00:00 GMT
        // System.out.println(timestamp.getTime());
        //output will like "1479249799770"

        // //format timestamp
        // System.out.println(sdf.format(timestamp));
        //output will like "2016.11.16.06.43.19"
        

}