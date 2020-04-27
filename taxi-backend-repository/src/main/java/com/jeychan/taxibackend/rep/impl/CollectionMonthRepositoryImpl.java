package com.jeychan.taxibackend.rep.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeychan.taxibackend.dao.entity.CollectionMonth;
import com.jeychan.taxibackend.dao.mapper.CollectionMonthMapper;
import com.jeychan.taxibackend.rep.service.CollectionMonthRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 17:02
 * motto : everything is no in vain
 * description
 */
@Repository
public class CollectionMonthRepositoryImpl extends ServiceImpl<CollectionMonthMapper, CollectionMonth>
        implements CollectionMonthRepository {

    @Resource
    private CollectionMonthMapper collectionMonthMapper;

    @Override
    public List<CollectionMonth> queryCollectionMonthByYear(int year) {
        QueryWrapper<CollectionMonth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CollectionMonth.YEAR, year);

        return collectionMonthMapper.selectList(queryWrapper);
    }
}
