package com.jeychan.taxibackend.service.service;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 14:34
 * motto : everything is no in vain
 * description
 */
public interface DateSelectService {

    /**
     * 返回可以提供选择的年份( 2016年之后 )
     *
     * @return 可以提供选择的年份集合
     */
    List<Integer> queryViewableYear();

    /**
     * 返回可以提供选择的月份（如果year是今年，返回的是当年当天月份之前的月份）
     *
     * @param year 查询年份
     * @return 可以提供选择的月份
     */
    List<Integer> queryViewableMonth(int year);

    /**
     * 返回可以提供选择的日期（如果year是今年且month是当月，返回的是当年当天之前的日期）
     *
     * @param year  年份
     * @param month 月份
     * @return 可以提供选择的日期
     */
    List<Integer> queryViewableDays(int year, int month);

    /**
     * 获取当前时间
     * @return 当前时间字符串
     */
    String queryCurrentTime();
}
