package com.jeychan.taxibackend.rep.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeychan.taxibackend.dao.entity.CollectionHour;
import com.jeychan.taxibackend.dao.entity.GeohashGps;
import com.jeychan.taxibackend.dao.mapper.CollectionHourMapper;
import com.jeychan.taxibackend.dao.mapper.GeohashGpsMapper;
import com.jeychan.taxibackend.rep.service.CollectionHourRepository;
import com.jeychan.taxibackend.rep.service.GeohashGpsRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 17:00
 * motto : everything is no in vain
 * description
 */
@Repository
public class CollectionHourRepositoryImpl extends ServiceImpl<CollectionHourMapper, CollectionHour>
        implements CollectionHourRepository {

    @Resource
    private CollectionHourMapper collectionHourMapper;

    @Override
    public List<CollectionHour> queryCollectionsHourByDate(LocalDate localDate) {
        QueryWrapper<CollectionHour> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CollectionHour.DATE, localDate);

        return collectionHourMapper.selectList(queryWrapper);
    }
}
