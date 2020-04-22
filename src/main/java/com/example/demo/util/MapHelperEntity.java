package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MapHelperEntity {

    @Autowired
    MapHelper maphelper;
    
    //latitude （緯度）, longtitude（經度）
    public void getDistance(double Latitude, double Longitude, int rc_id) throws Exception {
        /**
         * 第一種調用方法
         */

        double result = maphelper.getDistance(Latitude, Longitude, 25.082665, 121.557207);
        System.out.println("\n\n\n");
        System.out.println(result);
        System.out.println("\n\n\n");
        //double expected = 14.2243;
        //TestCase.assertEquals(expected, result);
    }


    public void getPointDistance() throws Exception {
        /**
         * 第二種調用方法
         */
        double result = maphelper.GetPointDistance("31.2553210000,121.4620020000", 4);
        System.out.println("\n\n\n");
        System.out.println(result);
        System.out.println("\n\n\n");
        //double expected = 14.2243;
        //TestCase.assertEquals(expected, result);
    }

    
}