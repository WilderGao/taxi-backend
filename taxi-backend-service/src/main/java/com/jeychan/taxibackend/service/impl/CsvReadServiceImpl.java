package com.jeychan.taxibackend.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jeychan.taxibackend.common.csv.CsvUtil;
import com.jeychan.taxibackend.dao.entity.Trip;
import com.jeychan.taxibackend.dao.entity.TripDropoff;
import com.jeychan.taxibackend.dao.entity.TripPickup;
import com.jeychan.taxibackend.rep.service.ITripDropoffRepository;
import com.jeychan.taxibackend.rep.service.ITripPickupRepository;
import com.jeychan.taxibackend.rep.service.ITripRepository;
import com.jeychan.taxibackend.service.domain.TaxiTrajectory;
import com.jeychan.taxibackend.service.interfaces.CsvReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WilderGao
 * time 2020-03-18 11:40
 * motto : everything is no in vain
 * description
 */
@Slf4j
@Service
public class CsvReadServiceImpl implements CsvReadService {

    @Autowired
    private ITripRepository iTripRepository;

    @Autowired
    private ITripPickupRepository iTripPickupRepository;

    @Autowired
    private ITripDropoffRepository iTripDropoffRepository;

    @Override
    public int importCsvDataToDatabase(String fileName, String filePath) {
        CsvAnalyseListenerOperator operator = new CsvAnalyseListenerOperator();
        CsvUtil.readCsv(filePath, fileName, operator, TaxiTrajectory.class);
        return 0;
    }


    class CsvAnalyseListenerOperator extends AnalysisEventListener<TaxiTrajectory> {
        private List<Trip> trips = new LinkedList<>();
        private List<TripPickup> tripPickups = new LinkedList<>();
        private List<TripDropoff> tripDropoffs = new LinkedList<>();

        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        private AtomicInteger id = new AtomicInteger(1);
        private static final int BATCH_SIZE = 50000;
        private DecimalFormat decimalFormat = new DecimalFormat(".00");
        private static final int MAX_SPEED_LIMIT = 100;

        @Override
        public void invoke(TaxiTrajectory taxiTrajectory, AnalysisContext analysisContext) {
            if (taxiTrajectory.getPickupDatetime().equals(taxiTrajectory.getDropOffDatetime())) {
                log.warn("CsvReadServiceImpl.CsvAnalyseListenerOperator.invoke: trash data, data={}", taxiTrajectory);
                return;
            } else if (taxiTrajectory.getPickupLongitude() == 0 && taxiTrajectory.getPickupLatitude() == 0 ||
                    taxiTrajectory.getDropOffLatitude() == 0 && taxiTrajectory.getDropOffLongitude() == 0) {
                log.warn("CsvReadServiceImpl.CsvAnalyseListenerOperator.invoke: trash data, data={}", taxiTrajectory);
                return;
            }

            int tripId = id.getAndIncrement();
            // 开始构造三个对应的类，trip、tripPickup、tripDropoff
            Trip trip = Trip.builder().id(tripId)
                    .pickupDate(LocalDateTime.parse(taxiTrajectory.getPickupDatetime(), formatter))
                    .dropoffDate(LocalDateTime.parse(taxiTrajectory.getDropOffDatetime(), formatter))
                    .passengerNum(taxiTrajectory.getPassengerCount())
                    .tripDistance(taxiTrajectory.getTripDistance())
                    .paymentType(taxiTrajectory.getPaymentType())
                    .totalAmount(taxiTrajectory.getTotalAmount()).build();
            //求平均速度
            double duringHour = duringHour(trip.getPickupDate(), trip.getDropoffDate());

            TripPickup tripPickup = TripPickup.builder()
                    .tripId(tripId)
                    .pickupDate(trip.getPickupDate())
                    .pickupLongitude(taxiTrajectory.getPickupLongitude())
                    .pickupLatitude(taxiTrajectory.getPickupLatitude())
                    .build();

            TripDropoff tripDropoff = TripDropoff.builder()
                    .tripId(tripId)
                    .dropoffDate(trip.getDropoffDate())
                    .dropoffLongitude(taxiTrajectory.getDropOffLongitude())
                    .dropoffLatitude(taxiTrajectory.getDropOffLatitude())
                    .build();
            try {

                //保留两位小数
                duringHour = Double.parseDouble(decimalFormat.format(duringHour));
                if (duringHour <= 0) {
                    log.warn("CsvReadServiceImpl.CsvAnalyseListenerOperator: " +
                            "duringHour not true, duringHour={}, trip={}", duringHour, trip);
                    return;
                }
                double avgSpeed = Double.parseDouble(decimalFormat.format(trip.getTripDistance() / duringHour));

                //极端数据判断，如果平均速度大于100则为脏数据
                if (avgSpeed > MAX_SPEED_LIMIT) {
                    log.warn("CsvReadServiceImpl.CsvAnalyseListenerOperator: " +
                            "not true data, avgSpeed={}, trip={}", avgSpeed, trip);
                    return;
                }

                trip.setAvgSpeed(avgSpeed);
                trips.add(trip);
                tripPickups.add(tripPickup);
                tripDropoffs.add(tripDropoff);

                if (trips.size() >= BATCH_SIZE) {
                    doAction();
                }
            } catch (Exception ex) {
                log.error("invoke exception, avgSpeed={}, duringHour={}, instance={}",
                        trip.getAvgSpeed(), duringHour, trip.getTripDistance());
                ex.printStackTrace();
            }
        }


        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            doAction();
        }

        private void doAction() {
            //批量插入数据库
            log.info("ExcelAnalyseListenerOperator.doAction: insert datas to mysql...");
            //插入之前要先排序，上下车都需要按照时间先后排序，后面显示热力图的时候Flink统计延时才不会太高
            //记得要将batch数量设置不能太低，否则局部排序的效果将不明显
            Collections.sort(tripPickups);
            Collections.sort(tripDropoffs);

            iTripRepository.saveBatch(trips);
            iTripPickupRepository.saveBatch(tripPickups);
            iTripDropoffRepository.saveBatch(tripDropoffs);

            trips.clear();
            tripPickups.clear();
            tripDropoffs.clear();
        }

        /**
         * 查询两个localDateTime的时间差（按照小时返回）
         *
         * @param pickUpDate  乘车时间
         * @param dropOffDate 下车时间
         * @return 时间差
         */
        private double duringHour(LocalDateTime pickUpDate, LocalDateTime dropOffDate) {
            int pickUpDay = pickUpDate.getDayOfMonth();
            int dropOffDay = dropOffDate.getDayOfMonth();

            int pickUpHour = pickUpDate.getHour();
            int dropOffHour = dropOffDate.getHour();
            int pickUpMin = pickUpDate.getMinute();
            int dropOffMin = dropOffDate.getMinute();

            return (dropOffDay - pickUpDay) * 24 + (dropOffHour - pickUpHour) + (dropOffMin - pickUpMin) / 60.0;
        }
    }
}
