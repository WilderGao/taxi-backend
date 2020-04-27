package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.CollectionMonth;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 17:02
 * motto : everything is no in vain
 * description
 */
public interface CollectionMonthRepository extends IService<CollectionMonth> {

    /**
     * 获取年份对应的月份统计信息
     *
     * @param year 年份
     * @return 统计信息集合
     */
    List<CollectionMonth> queryCollectionMonthByYear(int year);

}
