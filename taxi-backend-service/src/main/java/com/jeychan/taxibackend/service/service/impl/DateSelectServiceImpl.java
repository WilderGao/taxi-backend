package com.jeychan.taxibackend.service.service.impl;

import com.jeychan.taxibackend.common.util.LocalDateTimeUtil;
import com.jeychan.taxibackend.service.service.DateSelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 14:34
 * motto : everything is no in vain
 * description
 */
@Service
@Slf4j
public class DateSelectServiceImpl implements DateSelectService {
    /**
     * 最起始的年份：2016年
     */
    private static final int START_YEAR = 2016;

    /**
     * 最起始的月份：1月
     */
    private static final int START_MONTH = 1;

    /**
     * 最起始的日期：1日
     */
    private static final int START_DAY = 1;

    /**
     * 一年的12个月
     */
    private static final Integer[] MONTHS = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    @Override
    public List<Integer> queryViewableYear() {
        log.info("DateSelectServiceImpl.queryViewableYear: query viewable year...");
        LocalDateTime currentDateTime = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now());
        int endYear = currentDateTime.getYear();

        int viewableYear = START_YEAR;
        List<Integer> result = new ArrayList<>();
        while (viewableYear <= endYear) {
            result.add(viewableYear);
            viewableYear++;
        }
        log.info("DateSelectServiceImpl.queryViewableYear: result={}", result);
        return result;
    }


    @Override
    public List<Integer> queryViewableMonth(int year) {
        log.info("DateSelectServiceImpl.queryViewableMonth: query viewable month, year={}", year);
        LocalDateTime currentDateTime = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now());
        if (currentDateTime.getYear() > year) {
            return Arrays.asList(MONTHS);
        } else if (currentDateTime.getYear() == year) {
            //表示查询是的当年
            List<Integer> result = new ArrayList<>();
            int month = START_MONTH;
            while (month <= currentDateTime.getMonthValue()) {
                result.add(month);
                month++;
            }
            return result;
        } else {
            log.warn("DateSelectServiceImpl.queryViewableMonth warning: year is bigger than current year, year={}, currentYear={}",
                    year, currentDateTime.getYear());
            return null;
        }
    }

    @Override
    public List<Integer> queryViewableDays(int year, int month) {
        log.info("DateSelectServiceImpl.queryViewableMonth: query viewable days, year={}, month={}", year, month);
        LocalDateTime currentDateTime = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now());
        LocalDate currentDate = currentDateTime.toLocalDate();
        List<Integer> result = new ArrayList<>();
        int startDay = START_DAY;
        int monthOfDay;
        if (currentDate.getYear() == year && currentDate.getMonthValue() == month) {
            //表示查询的是当年当月
            log.info("DateSelectServiceImpl.queryViewableMonth: query current year and month");
            monthOfDay = currentDate.getDayOfMonth();
        } else if (currentDate.getYear() >= year && currentDate.getMonthValue() >= month) {
            monthOfDay = currentDate.lengthOfMonth();
        } else {
            log.warn("DateSelectServiceImpl.queryViewableDays warning: month is bigger than current month, year={}, month={}, " +
                    "currentYear={}, currentMonth={}", year, month, currentDateTime.getYear(), currentDateTime.getMonthValue());
            return null;
        }
        while (startDay <= monthOfDay) {
            result.add(startDay);
            startDay++;
        }
        return result;
    }
}
