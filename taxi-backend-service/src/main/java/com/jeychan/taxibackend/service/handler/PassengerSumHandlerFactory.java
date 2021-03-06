package com.jeychan.taxibackend.service.handler;

import com.jeychan.taxibackend.dao.entity.CollectionDay;
import com.jeychan.taxibackend.dao.entity.CollectionHour;
import com.jeychan.taxibackend.dao.entity.CollectionMonth;
import com.jeychan.taxibackend.service.vo.offline.CollectionDaysVo;
import com.jeychan.taxibackend.service.vo.offline.CollectionHoursVo;
import com.jeychan.taxibackend.service.vo.offline.CollectionMonthsVo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WilderGao
 * time 2020-04-20 23:46
 * motto : everything is no in vain
 * description 乘客集合处理
 */
public class PassengerSumHandlerFactory implements HandlerFactory {
    @Override
    public List<CollectionHoursVo> hourHandle(List<CollectionHour> collectionHours) {
        return collectionHours.stream().map(collectionHour ->
                CollectionHoursVo.builder()
                        .hour(collectionHour.getHour())
                        .count(collectionHour.getPassengerSum())
                        .avgSpeed(collectionHour.getAvgSpeed())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CollectionDaysVo> dayHandle(List<CollectionDay> collectionDays) {
        return collectionDays.stream().map(collectionDay -> {
            LocalDate localDate = LocalDate.of(collectionDay.getYear(), collectionDay.getMonth(), collectionDay.getDay());
            return CollectionDaysVo.builder()
                    .date(localDate)
                    .count(collectionDay.getPassengerSum())
                    .avgSpeed(collectionDay.getAvgSpeed())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<CollectionMonthsVo> monthHandle(List<CollectionMonth> collectionMonths) {
        return collectionMonths.stream().map(collectionMonth ->
                CollectionMonthsVo.builder()
                        .month(collectionMonth.getMonth())
                        .count(collectionMonth.getPassengerSum())
                        .avgSpeed(collectionMonth.getAvgSpeed())
                        .build()).collect(Collectors.toList());
    }
}
