package com.jeychan.taxibackend.service.handler;

import com.jeychan.taxibackend.dao.entity.CollectionDay;
import com.jeychan.taxibackend.dao.entity.CollectionHour;
import com.jeychan.taxibackend.dao.entity.CollectionMonth;
import com.jeychan.taxibackend.service.vo.offline.CollectionDaysVo;
import com.jeychan.taxibackend.service.vo.offline.CollectionHoursVo;
import com.jeychan.taxibackend.service.vo.offline.CollectionMonthsVo;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-20 23:31
 * motto : everything is no in vain
 * description 数据处理抽象工厂
 */
public interface HandlerFactory {
    /**
     * 小时集合处理方法
     *
     * @param collectionHours 小时集合
     * @return 处理结果
     */
    List<CollectionHoursVo> hourHandle(List<CollectionHour> collectionHours);

    /**
     * 天集合处理方法
     *
     * @param collectionDays 天集合
     * @return 处理结果
     */
    List<CollectionDaysVo> dayHandle(List<CollectionDay> collectionDays);

    /**
     * 月份集合处理方法
     *
     * @param collectionMonths 月份集合
     * @return 处理结果
     */
    List<CollectionMonthsVo> monthHandle(List<CollectionMonth> collectionMonths);
}
