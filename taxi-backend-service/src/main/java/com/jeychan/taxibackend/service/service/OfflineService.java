package com.jeychan.taxibackend.service.service;

import com.jeychan.taxibackend.common.exception.TaxiBackendBizException;
import com.jeychan.taxibackend.service.vo.offline.HistorySummaryVo;

/**
 * @author WilderGao
 * time 2020-04-18 16:35
 * motto : everything is no in vain
 * description 离线任务
 */
public interface OfflineService {

    /**
     * 小时统计定时任务
     */
    void offlineHourTask();

    /**
     * 天统计定时任务
     */
    void offlineDayTask();

    /**
     * 月份统计定时任务
     */
    void offlineMonthTask();

    /**
     * 根据年月日获取历史统计信息
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @param type  类型
     * @return 统计信息
     * @throws TaxiBackendBizException 抛出异常
     */
    HistorySummaryVo queryHistorySummary(Integer year, Integer month, Integer day, String type) throws TaxiBackendBizException;
}
