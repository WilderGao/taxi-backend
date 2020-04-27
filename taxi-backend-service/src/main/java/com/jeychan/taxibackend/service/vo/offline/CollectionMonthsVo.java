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
public class CollectionMonthsVo {
    /**
     * 对应月份
     */
    private int month;

    /**
     * 对应数量
     */
    private int count;

    /**
     * 对应平均速度
     */
    private double avgSpeed;
}
