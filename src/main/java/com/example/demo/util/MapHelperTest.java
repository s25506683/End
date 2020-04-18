package com.example.demo.util;

import org.springframework.stereotype.Repository;

@Repository
public class MapHelperTest {

    public void getDistance() throws Exception {
        /**
         * 第一種調用方法
         */
        double result = MapHelper.getDistance(25.036812, 121.431991, 25.082665, 121.557207);
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
        double result = MapHelper.GetPointDistance("31.2553210000,121.4620020000", "25.136697,121.502456");
        System.out.println("\n\n\n");
        System.out.println(result);
        System.out.println("\n\n\n");
        //double expected = 14.2243;
        //TestCase.assertEquals(expected, result);
    }
}