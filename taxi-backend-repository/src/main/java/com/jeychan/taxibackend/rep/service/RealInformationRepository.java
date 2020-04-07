package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.RealInformation;

import java.time.LocalDate;

/**
 * @author WilderGao
 * time 2020-04-07 14:40
 * motto : everything is no in vain
 * description
 */
public interface RealInformationRepository extends IService<RealInformation> {

    /**
     * 根据日期，小时，分钟拿到information
     * @param date
     * @param hour
     * @param min
     * @return
     */
    RealInformation queryInformationByDayHourMin(LocalDate date, int hour, int min);
}
