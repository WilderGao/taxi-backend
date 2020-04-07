package com.jeychan.taxibackend.service.domain.realtime;

import lombok.Data;

/**
 * @author WilderGao
 * time 2020-03-30 15:40
 * motto : everything is no in vain
 * description 区域统计情况
 */
@Data
public class DistinctInform {
    /**
     * 纽约市所属地区：1、Bronx; 2、Manhattan;3、Brooklyn; 4、Queens; 5、Staten Island
     */
    private int distinctId;

    /**
     * 上车总数
     */
    private int pickUpSum;

    /**
     * 下车总数
     */
    private int dropOffSum;
}
