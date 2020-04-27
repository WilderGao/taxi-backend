package com.jeychan.taxibackend.service.vo.offline;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-20 22:22
 * motto : everything is no in vain
 * description
 */
@Data
@Builder
public class HistorySummaryVo {

    /**
     * 小时统计信息集合
     */
    List<CollectionHoursVo> collectionHoursVos;

    /**
     * 天统计信息集合
     */
    List<CollectionDaysVo> collectionDaysVos;

    /**
     * 月统计信息集合
     */
    List<CollectionMonthsVo> collectionMonthsVos;
}
