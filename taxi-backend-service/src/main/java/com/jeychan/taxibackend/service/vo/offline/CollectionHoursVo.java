package com.jeychan.taxibackend.service.vo.offline;

import lombok.Builder;
import lombok.Data;

/**
 * @author WilderGao
 * time 2020-04-19 12:18
 * motto : everything is no in vain
 * description
 */
@Data
@Builder
public class CollectionHoursVo {
    /**
     * 对应小时
     */
    private int hour;

    /**
     * 对应数量
     */
    private int count;

    /**
     * 对应平均速度
     */
    private double avgSpeed;
}
