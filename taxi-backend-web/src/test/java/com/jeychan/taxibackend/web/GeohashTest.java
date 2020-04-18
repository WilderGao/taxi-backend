package com.jeychan.taxibackend.web;

import com.jeychan.taxibackend.common.operator.GeoHashOperator;
import org.junit.Test;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-03-28 23:15
 * motto : everything is no in vain
 * description geohash测试
 */
public class GeohashTest {

    @Test
    public void test() {
        /*
         * Staten Island : [-74.2562,40.4955],[-74.0516,40.6494]
         * The Bronx : [-73.9335, 40.7850], [-73.7646, 40.9158]
         * Brooklyn : [-74.0424, 40.5696], [-73.8566, 40.7389]
         * Manhattan: [-74.0190, 40.7002], [-73.9057, 40.8795]
         * Queens: [-73.9476, 40.5383], [-73.6977, 40.8112]
         */
        double longMin = -74.2562;
        double longMax =-74.0516;
        double latMin = 40.4955;
        double latMax = 40.6494;


        List<String> geohashs = GeoHashOperator.getAreaGeoHashs(latMin, longMin, latMax, longMax, 5);
        geohashs.forEach(geohash -> System.out.println(geohash + "-->" + GeoHashOperator.getGpsByGeoHash(geohash)));
    }
}
