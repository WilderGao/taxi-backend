package com.jeychan.taxibackend.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeychan.taxibackend.common.enums.Distinct;
import com.jeychan.taxibackend.common.operator.WebSocketOperator;
import com.jeychan.taxibackend.dao.entity.GeohashGps;
import com.jeychan.taxibackend.dao.entity.RealInformation;
import com.jeychan.taxibackend.dao.entity.RealInformationDetail;
import com.jeychan.taxibackend.rep.service.GeohashGpsRepository;
import com.jeychan.taxibackend.rep.service.RealInformationDetailRepository;
import com.jeychan.taxibackend.rep.service.RealInformationRepository;
import com.jeychan.taxibackend.service.domain.realtime.DistinctInform;
import com.jeychan.taxibackend.service.domain.realtime.GpsInform;
import com.jeychan.taxibackend.service.domain.realtime.RealTimeInform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WilderGao
 * time 2020-03-30 15:56
 * motto : everything is no in vain
 * description
 */
@EnableScheduling
@Configuration
@Slf4j
public class RealTimeService {
    @Resource
    private WebSocketOperator webSocketOperator;

    @Resource
    private RealInformationRepository realInformationRepository;

    @Resource
    private RealInformationDetailRepository realInformationDetailRepository;

    @Resource
    private GeohashGpsRepository geohashGpsRepository;

    private static final int DEFAULT_MAP_SIZE = 32;

    @Scheduled(fixedRate = 15000)
    private void scheduleTask() {
        Map<String, Session> clients = WebSocketOperator.getClients();
        //获取当前时间(经过格式化后的时间)
        LocalDateTime now = queryCurrentTime(LocalDateTime.now());
        log.info("RealTimeService.scheduleTask: realtime location send, dateTime={}, clients={}", now, clients);
        //拿到当前时间的realInformation
        RealInformation realInformation = realInformationRepository.queryInformationByDayHourMin(now.toLocalDate(),
                now.getHour(), now.getMinute());

        log.info("RealTimeService.scheduleTask: query realInformation, data={}", realInformation);
        if (realInformation == null || realInformation.getId() == null) {
            log.warn("RealTimeService.scheduleTask: realInformation or id is null, time={}", now);
            return;
        }

        List<RealInformationDetail> details = realInformationDetailRepository.getDetailsById(realInformation.getId());
        log.info("RealTimeService.scheduleTask: query details, {}", JSONObject.toJSONString(details));

        //构建RealTimeInform
        RealTimeInform realTimeInform = packageMessage(realInformation, details);

        clients.forEach((id, session) -> {
            try {
                //todo 前端关闭之后这里不会自动删除，要注意一下
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(JSONObject.toJSONString(realTimeInform), true);
                } else {
                    log.info("session is not open, will be deleted, session={}", session);
                    clients.remove(id);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 因为要用流式数据，要把今天当作2016-01-01，需要求出时间差
     *
     * @return 截取后的当前时间
     */
    public static LocalDateTime queryCurrentTime(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse("2016-01-01 00:00", formatter);

        //作为参考系时间，求出当前时间与参考系时间相差多少天和多少秒
        LocalDateTime checkDateTime = LocalDateTime.parse("2020-04-08 00:10", formatter);
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

    /**
     * 根据sql中的结果构建websocket消息
     *
     * @param realInformation 总结信息
     * @param details         详情信息
     * @return 打包结果
     */
    private RealTimeInform packageMessage(RealInformation realInformation, List<RealInformationDetail> details) {

        RealTimeInform realTimeInform = new RealTimeInform();
        Map<Integer, DistinctInform> distinctInformMap = new HashMap<>(DEFAULT_MAP_SIZE / 4);
        Distinct[] dicts = Distinct.values();
        for (Distinct dict : dicts) {
            int distinctId = dict.getDistinctId();
            distinctInformMap.put(distinctId, new DistinctInform());
        }

        Map<Integer, List<RealInformationDetail>> typeGroupByMap =
                details.stream().collect(Collectors.groupingBy(RealInformationDetail::getType, Collectors.toList()));

        typeGroupByMap.forEach((type, indexDetails) -> {
            List<GpsInform> gpsInforms = indexDetails.stream().map(detail -> {
                GeohashGps geohashGps = geohashGpsRepository.getGeohashGpsByGeohash(detail.getGeohash());
                //构建gpsInform
                return GpsInform.builder()
                        .distinctId(geohashGps.getDist())
                        .longitude(geohashGps.getLongitude())
                        .latitude(geohashGps.getLatitude())
                        .num(detail.getOrderNum())
                        .type(detail.getType())
                        .build();
            }).collect(Collectors.toList());

            if (type == RealInformationDetail.PICK_UP) {
                realTimeInform.setGpsInformsPickup(gpsInforms);
            } else if (type == RealInformationDetail.DROP_OFF) {
                realTimeInform.setGpsInformsDropoff(gpsInforms);
            }

            //将gpsInforms集合按照区域Id划分（5个区域这里应该循环5次），计算每个区域的上下车总量，把结果放在distinctInform中
            gpsInforms.stream().collect(Collectors.groupingBy(GpsInform::getDistinctId, Collectors.toList()))
                    .forEach((distinctId, indexGpsInforms) -> {
                        DistinctInform distinctInform = distinctInformMap.get(distinctId);
                        distinctInform.setDistinctId(distinctId);

                        //计算总量，如果type是上车就是上车总量，反之就是下车总量
                        int sum = indexGpsInforms.stream().mapToInt(GpsInform::getNum).sum();
                        if (type == RealInformationDetail.PICK_UP) {
                            distinctInform.setPickUpSum(sum);
                        } else if (type == RealInformationDetail.DROP_OFF) {
                            distinctInform.setDropOffSum(sum);
                        }
                    });
        });

        List<DistinctInform> finalDistinctInforms = new ArrayList<>();
        distinctInformMap.forEach((k, v) -> finalDistinctInforms.add(v));

        realTimeInform.setDistinctInforms(finalDistinctInforms);

        //补全其它信息
        LocalDateTime startDateTime = packageLocalDateTime(realInformation.getStartDay(), realInformation.getStartHour(),
                realInformation.getStartMin());
        LocalDateTime endDateTime = packageLocalDateTime(realInformation.getEndDay(), realInformation.getEndHour(),
                realInformation.getEndMin());
        realTimeInform.setStartTime(startDateTime);
        realTimeInform.setEndTime(endDateTime);

        realTimeInform.setAvgSpeed(realInformation.getAvgSpeed());
        realTimeInform.setOrderNum(realInformation.getOrderNum());
        realTimeInform.setPassengerNum(realInformation.getPassengerNum());
        realInformation.setTotalAmount(realInformation.getTotalAmount());

        return realTimeInform;
    }

    /**
     * 构建localDateTime
     *
     * @param date   日期
     * @param hour   小时
     * @param minute 分钟
     * @return localDateTime
     */
    private LocalDateTime packageLocalDateTime(LocalDate date, int hour, int minute) {
        LocalTime localTime = LocalTime.of(hour, minute);
        return LocalDateTime.of(date, localTime);
    }
}
