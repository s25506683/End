package com.example.demo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CurrentTimeStamp {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Timestamp gettimestamp = new Timestamp(System.currentTimeMillis());
    
    String timestamp = df.format(gettimestamp);

    public String getCurrentTimeStamp(){
        return timestamp;
    }
    

}