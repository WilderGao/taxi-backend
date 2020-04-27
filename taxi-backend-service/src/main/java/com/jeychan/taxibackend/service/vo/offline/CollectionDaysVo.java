package com.jeychan.taxibackend.service.vo.offline;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author WilderGao
 * time 2020-04-19 12:17
 * motto : everything is no in vain
 * description
 */
@Data
@Builder
public class CollectionDaysVo {
    /**
     * 对应日期
     */
    private LocalDate date;

    /**
     * 对应数量
     */
    private int count;

    /**
     * 对应平均速度
     */
    private double avgSpeed;
}
