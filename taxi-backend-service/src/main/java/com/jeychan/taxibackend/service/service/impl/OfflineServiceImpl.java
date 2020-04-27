package com.jeychan.taxibackend.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeychan.taxibackend.common.enums.BizErrorCode;
import com.jeychan.taxibackend.common.exception.TaxiBackendBizException;
import com.jeychan.taxibackend.common.util.LocalDateTimeUtil;
import com.jeychan.taxibackend.dao.entity.CollectionDay;
import com.jeychan.taxibackend.dao.entity.CollectionHour;
import com.jeychan.taxibackend.dao.entity.CollectionMonth;
import com.jeychan.taxibackend.dao.entity.RealInformation;
import com.jeychan.taxibackend.rep.service.CollectionDayRepository;
import com.jeychan.taxibackend.rep.service.CollectionHourRepository;
import com.jeychan.taxibackend.rep.service.CollectionMonthRepository;
import com.jeychan.taxibackend.rep.service.RealInformationRepository;
import com.jeychan.taxibackend.service.handler.HandlerFactory;
import com.jeychan.taxibackend.service.handler.OrderSumHandlerFactory;
import com.jeychan.taxibackend.service.handler.PassengerSumHandlerFactory;
import com.jeychan.taxibackend.service.service.OfflineService;
import com.jeychan.taxibackend.service.vo.offline.CollectionDaysVo;
import com.jeychan.taxibackend.service.vo.offline.CollectionHoursVo;
import com.jeychan.taxibackend.service.vo.offline.CollectionMonthsVo;
import com.jeychan.taxibackend.service.vo.offline.HistorySummaryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;

/**
 * @author WilderGao
 * time 2020-04-18 17:06
 * motto : everything is no in vain
 * description
 */
@Slf4j
@EnableScheduling
@Configuration
public class OfflineServiceImpl implements OfflineService {
    @Resource
    private RealInformationRepository realInformationRepository;

    @Resource
    private CollectionHourRepository hourRepository;

    @Resource
    private CollectionDayRepository dayRepository;

    @Resource
    private CollectionMonthRepository monthRepository;

    private static final long MINUS_NUM = 1L;

    /**
     * 两种不同的查询类型（订单数量和乘客数量）
     */
    private static final String ORDER_SUM = "订单";
    private static final String PASSENGER_SUM = "乘客";


    //todo 所有模块都没有做异常捕获和抛出，后面记得写完

    @Override
    @Scheduled(cron = "${exp.tbl.hour.cron}")
    public void offlineHourTask() {
        /*
         * 1. 获取当前时间
         * 2. 根据当前时间上一个小时的时间从数据库找数据进行统计
         * 3. 统计结果输出到小时统计表
         */
        LocalDateTime currentTime = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now());
        log.info("OfflineServiceImpl.offlineHourTask info: currentTime={}", JSONObject.toJSONString(currentTime));

        //查询的是当前时间的上一个小时
        LocalDateTime beforeOneHourTime = currentTime.minusHours(MINUS_NUM);
        List<RealInformation> informations = realInformationRepository.queryInformationByDayHour
                (beforeOneHourTime.toLocalDate(), beforeOneHourTime.getHour());
        if (CollectionUtils.isEmpty(informations)) {
            log.error("OfflineServiceImpl.offlineHourTask error: information result empty, currentTime={}", currentTime);
            return;
        }
        log.info("OfflineServiceImpl.offlineHourTask info: information result={}", informations);

        //统计订单总量、平均速度和乘客数量
        int orderSum = informations.stream().mapToInt(RealInformation::getOrderNum).sum();
        int passengerSum = informations.stream().mapToInt(RealInformation::getPassengerNum).sum();
        OptionalDouble avgSpeedOption = informations.stream().mapToDouble(RealInformation::getAvgSpeed).average();
        double totalAmount = informations.stream().mapToDouble(RealInformation::getTotalAmount).sum();

        double avgSpeed = 0;
        if (avgSpeedOption.isPresent()) {
            avgSpeed = avgSpeedOption.getAsDouble();
        }

        CollectionHour collectionHour = CollectionHour.builder()
                .date(beforeOneHourTime.toLocalDate())
                .hour(beforeOneHourTime.getHour())
                .avgSpeed(avgSpeed)
                .orderSum(orderSum)
                .passengerSum(passengerSum)
                .totalAmount(totalAmount)
                .build();
        log.info("OfflineServiceImpl.offlineHourTask info: collectionHour={}", JSONObject.toJSONString(collectionHour));

        hourRepository.save(collectionHour);
    }

    @Override
    @Scheduled(cron = "${exp.tbl.day.cron}")
    public void offlineDayTask() {
        /*
         * 1、一天触发一次，查询小时纬度表，查询上一天的数据
         * 2、统计上一天的数据并保存到天纬度表
         */
        LocalDateTime currentTime = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now());
        LocalDateTime beforeOneDayDateTime = currentTime.minusDays(MINUS_NUM);

        log.info("OfflineServiceImpl.offlineDayTask info: currentTime={}, beforeOneDayTime={}", currentTime, beforeOneDayDateTime);

        List<CollectionHour> collectionHours = hourRepository.queryCollectionsHourByDate(beforeOneDayDateTime.toLocalDate());
        if (CollectionUtils.isEmpty(collectionHours)) {
            log.error("OfflineServiceImpl.offlineDayTask error: select result is empty, currentTime={}", currentTime);
            return;
        }
        //开始做统计信息
        int passengerSum = collectionHours.stream().mapToInt(CollectionHour::getPassengerSum).sum();
        int orderSum = collectionHours.stream().mapToInt(CollectionHour::getOrderSum).sum();
        double avgSpeed = 0;
        OptionalDouble avgSpeedOptional = collectionHours.stream().mapToDouble(CollectionHour::getAvgSpeed).average();
        if (avgSpeedOptional.isPresent()) {
            avgSpeed = avgSpeedOptional.getAsDouble();
        }
        double totalAmount = collectionHours.stream().mapToDouble(CollectionHour::getTotalAmount).sum();

        CollectionDay collectionDay = CollectionDay.builder()
                .year(beforeOneDayDateTime.getYear())
                .month(beforeOneDayDateTime.getMonthValue())
                .day(beforeOneDayDateTime.getDayOfMonth())
                .passengerSum(passengerSum)
                .orderSum(orderSum)
                .avgSpeed(avgSpeed)
                .totalAmount(totalAmount)
                .build();
        log.info("OfflineServiceImpl.offlineDayTask info: task collect result, collectionDay={}", JSONObject.toJSONString(collectionDay));

        dayRepository.save(collectionDay);
    }

    @Override
    @Scheduled(cron = "${exp.tbl.month.cron}")
    public void offlineMonthTask() {
        /*
         * 1、一天触发一次，查询小时纬度表，查询上一天的数据
         * 2、统计上一天的数据并保存到天纬度表
         */
        LocalDateTime currentTime = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now());
        LocalDateTime beforeOneMonthDateTime = currentTime.minusMonths(MINUS_NUM);

        log.info("OfflineServiceImpl.offlineMonthTask info: currentTime={}, beforeOneMonthTime={}", currentTime, beforeOneMonthDateTime);

        List<CollectionDay> collectionDays = dayRepository.queryCollectionDaysByYearAndMonth(beforeOneMonthDateTime.getYear(),
                beforeOneMonthDateTime.getMonthValue());
        if (CollectionUtils.isEmpty(collectionDays)) {
            log.error("OfflineServiceImpl.offlineMonthTask error: select result is empty, currentTime={}, beforeOneMonthTime={}",
                    JSONObject.toJSONString(currentTime), JSONObject.toJSONString(beforeOneMonthDateTime));
            return;
        }
        log.info("OfflineServiceImpl.offlineMonthTask info: collectionDays={}", JSONObject.toJSONString(collectionDays));

        double avgSpeed = 0;
        int passengerSum = collectionDays.stream().mapToInt(CollectionDay::getPassengerSum).sum();
        int orderSum = collectionDays.stream().mapToInt(CollectionDay::getOrderSum).sum();
        double totalAmount = collectionDays.stream().mapToDouble(CollectionDay::getTotalAmount).sum();

        OptionalDouble avgSpeedOptional = collectionDays.stream().mapToDouble(CollectionDay::getAvgSpeed).average();
        if (avgSpeedOptional.isPresent()) {
            avgSpeed = avgSpeedOptional.getAsDouble();
        }
        if (avgSpeed == 0) {
            log.error("OfflineServiceImpl.offlineMonthTask error: avgSpeed is zero, collectionsDay = {}", collectionDays);
            //todo 平均速度为0的时候是否要做一些操作？？？
        }

        CollectionMonth collectionMonth = CollectionMonth.builder()
                .year(beforeOneMonthDateTime.getYear())
                .month(beforeOneMonthDateTime.getMonthValue())
                .passengerSum(passengerSum)
                .orderSum(orderSum)
                .totalAmount(totalAmount)
                .avgSpeed(avgSpeed)
                .build();

        log.info("OfflineServiceImpl.offlineDayTask info: task collect result, collectionMonth={}", JSONObject.toJSONString(collectionMonth));

        monthRepository.save(collectionMonth);
    }

    @Override
    public HistorySummaryVo queryHistorySummary(Integer year, Integer month, Integer day, String type) {
        if (year == null || month == null || day == null || type == null) {
            log.error("OfflineServiceImpl.queryHistorySummary error: parameter null, year={}, " +
                    "month={}, day={}, type={}", year, month, day, type);
            return null;
        }

        LocalDate queryLocalDate = LocalDate.of(year, month, day);
        log.info("OfflineServiceImpl.queryHistorySummary info: date={}, type={}", JSONObject.toJSONString(queryLocalDate), type);
        LocalDate currentDate = LocalDateTimeUtil.queryCurrentTime(LocalDateTime.now()).toLocalDate();
        if (queryLocalDate.isAfter(currentDate)) {
            //查询时间在当前时间之后，说明有问题直接返回
            log.error("OfflineServiceImpl.queryHistorySummary error: queryDateTime is after currentTime, currentTime={}, queryTime={}",
                    JSONObject.toJSONString(currentDate), JSONObject.toJSONString(queryLocalDate));
            throw new TaxiBackendBizException(BizErrorCode.DATE_TIME_INVALID);
        }
        try {
            List<CollectionHour> historyCollectionsHour = hourRepository.queryCollectionsHourByDate(queryLocalDate);
            log.info("OfflineServiceImpl.queryHistorySummary info: query historyCollectionsHour, data={}", historyCollectionsHour);

            List<CollectionDay> historyCollectionsDay = dayRepository.queryCollectionDaysByYearAndMonth(year, month);
            log.info("OfflineServiceImpl.queryHistorySummary info: query historyCollectionsDay, data={}", historyCollectionsDay);

            List<CollectionMonth> historyCollectionsMonth = monthRepository.queryCollectionMonthByYear(year);
            log.info("OfflineServiceImpl.queryHistorySummary info: query historyCollectionsDay, data={}", historyCollectionsMonth);

            HandlerFactory handlerFactory = queryHandlerFactoryByType(type);
            List<CollectionHoursVo> collectionHoursVos = handlerFactory.hourHandle(historyCollectionsHour);
            List<CollectionDaysVo> collectionDaysVos = handlerFactory.dayHandle(historyCollectionsDay);
            List<CollectionMonthsVo> collectionMonthsVos = handlerFactory.monthHandle(historyCollectionsMonth);

            HistorySummaryVo historySummaryVo = HistorySummaryVo.builder()
                    .collectionHoursVos(collectionHoursVos)
                    .collectionDaysVos(collectionDaysVos)
                    .collectionMonthsVos(collectionMonthsVos)
                    .build();
            log.info("OfflineServiceImpl.queryHistorySummary info: final historySummaryVo, data={}", JSONObject.toJSONString(historySummaryVo));

            return historySummaryVo;
        } catch (Exception ex) {
            log.error("OfflineServiceImpl.queryHistorySummary error: find exception, errorMsg={}", ex.getMessage());
            throw new TaxiBackendBizException(BizErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 根据类型返回对应的处理工厂
     *
     * @param type 类型
     * @return 处理工厂
     */
    private HandlerFactory queryHandlerFactoryByType(String type) {
        switch (type) {
            case ORDER_SUM:
                return new OrderSumHandlerFactory();
            case PASSENGER_SUM:
                return new PassengerSumHandlerFactory();
            default:
                throw new TaxiBackendBizException(BizErrorCode.TYPE_INVALID);
        }
    }
}
