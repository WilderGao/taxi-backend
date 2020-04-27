package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.RealInformation;

import java.time.LocalDate;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-07 14:40
 * motto : everything is no in vain
 * description
 */
public interface RealInformationRepository extends IService<RealInformation> {

    /**
     * 根据日期，小时，分钟拿到information
     *
     * @param date 日期
     * @param hour 小时
     * @param min  分钟
     * @return 查询结果
     */
    RealInformation queryInformationByDayHourMin(LocalDate date, int hour, int min);

    /**
     * 根据日期，小时，分钟拿到information集合
     *
     * @param date 日期
     * @param hour 小时
     * @return 查询结果
     */
    List<RealInformation> queryInformationByDayHour(LocalDate date, int hour);
}
