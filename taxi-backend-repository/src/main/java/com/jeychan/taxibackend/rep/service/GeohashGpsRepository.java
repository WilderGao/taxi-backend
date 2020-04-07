package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.GeohashGps;

/**
 * @author WilderGao
 * time 2020-04-07 14:55
 * motto : everything is no in vain
 * description
 */
public interface GeohashGpsRepository extends IService<GeohashGps> {

    /**
     * 通过geohash拿到gps
     *
     * @param geohash geohash
     * @return gps
     */
    GeohashGps getGeohashGpsByGeohash(String geohash);
}
