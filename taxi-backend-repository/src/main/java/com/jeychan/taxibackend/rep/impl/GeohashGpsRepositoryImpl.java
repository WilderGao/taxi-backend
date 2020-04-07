package com.jeychan.taxibackend.rep.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeychan.taxibackend.dao.entity.GeohashGps;
import com.jeychan.taxibackend.dao.mapper.GeohashGpsMapper;
import com.jeychan.taxibackend.rep.service.GeohashGpsRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author WilderGao
 * time 2020-04-07 14:55
 * motto : everything is no in vain
 * description
 */
@Repository
public class GeohashGpsRepositoryImpl extends ServiceImpl<GeohashGpsMapper, GeohashGps>
        implements GeohashGpsRepository {

    @Resource
    private GeohashGpsMapper geohashGpsMapper;

    @Override
    @Cacheable(key = "#geohash", value = "gps")
    public GeohashGps getGeohashGpsByGeohash(String geohash) {

        QueryWrapper<GeohashGps> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(GeohashGps.GEOHASH, geohash);

        return geohashGpsMapper.selectOne(queryWrapper);
    }
}
