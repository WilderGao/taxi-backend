package com.jeychan.taxibackend.service.vo.realtime;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-03-30 15:33
 * motto : everything is no in vain
 * description 业务对象：websocket传递的统计结果对象
 */
@Data
public class RealTimeInformVo {
    /**
     * 起始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 地图实时信息数据集 —— 上车
     */
    private List<GpsInformVo> gpsInformsPickup;

    /**
     * 地图实时信息数据集 —— 下车
     */
    private List<GpsInformVo> gpsInformsDropoff;

    /**
     * 区域实时统计结果
     */
    private List<DistinctInformVo> distinctInforms;

    /**
     * 上车人数
     */
    private int pickupNum;

    /**
     * 乘客数量
     */
    private int passengerNum;

    /**
     * 下车人数
     */
    private int dropOffNum;

    /**
     * 订单总数
     */
    private int orderNum;

    /**
     * 平均速度
     */
    private double avgSpeed;

    /**
     * 总金额
     */
    private double totalAmount;

}
