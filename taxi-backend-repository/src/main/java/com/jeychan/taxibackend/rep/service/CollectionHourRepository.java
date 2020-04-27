package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.CollectionHour;

import java.time.LocalDate;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 16:59
 * motto : everything is no in vain
 * description
 */
public interface CollectionHourRepository extends IService<CollectionHour> {

    /**
     * 根据查询日期获取对应的小时统计信息表
     *
     * @param localDate 查询日期
     * @return 小时统计信息集合
     */
    List<CollectionHour> queryCollectionsHourByDate(LocalDate localDate);


}
