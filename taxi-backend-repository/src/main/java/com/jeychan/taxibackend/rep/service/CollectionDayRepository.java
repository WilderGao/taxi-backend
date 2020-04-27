package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.CollectionDay;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 17:01
 * motto : everything is no in vain
 * description
 */
public interface CollectionDayRepository extends IService<CollectionDay> {

    /**
     * 根据年份和月份查询对应内容
     *
     * @param year  年份
     * @param month 月份
     * @return 对应内容
     */
    List<CollectionDay> queryCollectionDaysByYearAndMonth(int year, int month);
}
