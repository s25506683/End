package com.example.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dao.MapHelperDAO;

//import com.example.demo.util.MapHelperEntity;

@Repository
public class MapHelper {

    @Autowired
    MapHelperDAO maphelperdao;

    /**
     * 地球半徑
     */
    private double EarthRadius = 6378.137;

    /**
     * 經緯度轉化成弧度 Add by 成長的小豬（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param d 經度/緯度
     * @return
     */
    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 計算兩個座標點之間的距離 Add by 成長的小豬（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param Latitude          第一個座標的緯度
     * @param Longitude         第一個座標的經度
     * @param DatabaseLatitude  第二個座標的緯度
     * @param DatabaseLongitude 第二個座標的經度
     * @return 返回兩點之間的距離，單位：公里/千米
     */
    public double getDistance(double Latitude, double Longitude, double DatabaseLatitude, double DatabaseLongitude) {
        double firstRadLat = rad(Latitude);
        double firstRadLng = rad(Longitude);
        double secondRadLat = rad(DatabaseLatitude);
        double secondRadLng = rad(DatabaseLongitude);

        double a = firstRadLat - secondRadLat;
        double b = firstRadLng - secondRadLng;
        double cal = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(firstRadLat) * Math.cos(secondRadLat) * Math.pow(Math.sin(b / 2), 2))) * EarthRadius;
        double result = Math.round(cal * 10000d) / 10000d;
        return result;
    }

    /**
     * 計算兩個座標點之間的距離 Add by 成長的小豬（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param Point         第一個座標點的（緯度,經度） 例如："31.2553210000,121.4620020000"
     * @param DatabasePoint 第二個座標點的（緯度,經度） 例如："31.2005470000,121.3269970000"
     * @return 返回兩點之間的距離，單位：公里/千米
     */
    public double GetPointDistance(String Point, int rc_id) {
        String[] firstArray = Point.split(",");
        
        String[] secondArray = maphelperdao.GetDatabase(rc_id).split(",");
        //String[] secondArray = DatabasePoint.split(",");
        double firstLatitude = Double.valueOf(firstArray[1].trim());
        double firstLongitude = Double.valueOf(firstArray[0].trim());
        double secondLatitude = Double.valueOf(secondArray[1].trim());
        double secondLongitude = Double.valueOf(secondArray[0].trim());
        return getDistance(firstLatitude, firstLongitude, secondLatitude, secondLongitude);
    }

    





}