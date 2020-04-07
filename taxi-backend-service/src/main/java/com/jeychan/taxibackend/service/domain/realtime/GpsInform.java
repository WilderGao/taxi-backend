package com.jeychan.taxibackend.service.domain.realtime;

import lombok.Builder;
import lombok.Data;

/**
 * @author WilderGao
 * time 2020-03-30 15:36
 * motto : everything is no in vain
 * description gps和数量信息
 */
@Data
@Builder
public class GpsInform {
    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 数量
     */
    private int num;

    /**
     * 1、上车；2、下车
     */
    private int type;

    /**
     * 所属区域：1、Bronx; 2、Manhattan;3、Brooklyn; 4、Queens; 5、Staten Island
     */
    private int distinctId;
}
