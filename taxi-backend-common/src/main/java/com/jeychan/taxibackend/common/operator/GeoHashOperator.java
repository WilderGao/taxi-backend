package com.jeychan.taxibackend.common.operator;

import java.text.DecimalFormat;
import java.util.LinkedList;

import ch.hsr.geohash.GeoHash;

/**
 * @author WilderGao
 * time 2020-03-28 22:58
 * motto : everything is no in vain
 * description geohash操作
 */
public class GeoHashOperator {
    /**
     * 通过左下角右上角两个经纬度坐标来得到整个矩形范围的GeoHash集合
     *
     * @param latMin 最小纬度
     * @param lonMin 最小经度
     * @param latMax 最大纬度
     * @param lonMax 最大经度
     * @param precis 想要得到的字符串长度，长度越长代表划分的格子越小
     * @return 整个矩形范围的GeoHash集合
     */
    public static LinkedList<String> getAreaGeoHashs(double latMin, double lonMin, double latMax, double lonMax, int precis) {
        if (latMin > latMax || lonMin > lonMax) {
            return null;
        }
        LinkedList<String> geoList = new LinkedList<>();
        GeoHash ld = GeoHash.withCharacterPrecision(latMin, lonMin, precis);
        GeoHash rd = GeoHash.withCharacterPrecision(latMin, lonMax, precis);
        GeoHash lu = GeoHash.withCharacterPrecision(latMax, lonMin, precis);

        int goflat = 1;
        int goup = 1;
        GeoHash tmp = ld;
        while (!tmp.equals(rd)) {
            tmp = tmp.getEasternNeighbour();
            goflat++;
        }

        tmp = lu;
        while (!tmp.equals(ld)) {
            tmp = tmp.getSouthernNeighbour();
            goup++;
        }
        tmp = lu;
        for (int i = 0; i < goflat; i++) {
            GeoHash tmp2 = tmp;
            geoList.add(tmp2.toBase32());
            tmp = tmp.getEasternNeighbour();
            for (int j = goup - 2; j >= 0; j--) {
                tmp2 = tmp2.getSouthernNeighbour();
                geoList.add(tmp2.toBase32());
            }
        }
        return geoList;
    }

    /**
     * 通过geoHash字符串来返回对应的经纬度坐标
     *
     * @param geoHashString geoHash字符串
     * @return 经纬度
     */
    public static String getGpsByGeoHash(String geoHashString) {
        //设置精度
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        //得到GeoHash对象
        GeoHash geoHash = GeoHash.fromGeohashString(geoHashString);
        StringBuilder geoHashPoint = new StringBuilder(geoHash.getPoint().toString());
        //切掉两个括号
        geoHashPoint.delete(0, 1);
        geoHashPoint.delete(geoHashPoint.length() - 1, geoHashPoint.length());
        String[] gpsPointArray = geoHashPoint.toString().split(",");
        double x = Double.parseDouble(decimalFormat.format(Double.parseDouble(gpsPointArray[0])));
        double y = Double.parseDouble(decimalFormat.format(Double.parseDouble(gpsPointArray[1])));

        x = x - (0.01 / 85 * 38) + (0.01 / 85 * 76 * Math.random());
        y = y - (0.01 / 111 * 38) + (0.01 / 111 * 76 * Math.random());

        return x + "," + y;
    }
}
