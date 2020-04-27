package com.jeychan.taxibackend.rep.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeychan.taxibackend.dao.entity.CollectionDay;
import com.jeychan.taxibackend.dao.mapper.CollectionDayMapper;
import com.jeychan.taxibackend.rep.service.CollectionDayRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 17:01
 * motto : everything is no in vain
 * description
 */
@Repository
public class CollectionDayRepositoryImpl extends ServiceImpl<CollectionDayMapper, CollectionDay>
        implements CollectionDayRepository {

    @Resource
    private CollectionDayMapper collectionDayMapper;

    @Override
    public List<CollectionDay> queryCollectionDaysByYearAndMonth(int year, int month) {
        QueryWrapper<CollectionDay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CollectionDay.YEAR, year)
                .eq(CollectionDay.MONTH, month);
        return collectionDayMapper.selectList(queryWrapper);
    }
}
