package com.jeychan.taxibackend.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author WilderGao
 * time 2020-04-18 14:42
 * motto : everything is no in vain
 * description
 */
public class LocalDateTimeUtil {

    /**
     * 因为要用流式数据，要把今天当作2016-01-01，需要求出时间差
     *
     * @return 截取后的当前时间
     */
    public static LocalDateTime queryCurrentTime(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse("2016-01-01 00:00", formatter);

        //作为参考系时间，求出当前时间与参考系时间相差多少天和多少秒
        LocalDateTime checkDateTime = LocalDateTime.parse("2020-04-21 16:50", formatter);
        long totalSubDay = now.toLocalDate().toEpochDay() - checkDateTime.toLocalDate().toEpochDay();
        long totalSubSec = now.toLocalTime().toSecondOfDay() - checkDateTime.toLocalTime().toSecondOfDay();

        //拿到偏移量之后，根据startDateTime作为起始时间进行天数和秒数的增加
        LocalDateTime plusDayDateTime = startDateTime.plusDays(totalSubDay);
        LocalDateTime plusSecDateTime = plusDayDateTime.plusSeconds(totalSubSec);

        //拿到之后对分钟数进行近似值获取，拿到当前时间的上一个时间段，比如现在是16:55，就要拿到16:40~16:50这个时间段的结果，也就是返回16:40
        int minute = plusSecDateTime.getMinute();

        int index = (minute - 1) / 10;
        int finalSubMin = minute - (index * 10) + 10;

        return plusSecDateTime.minusMinutes(finalSubMin);
    }
}
