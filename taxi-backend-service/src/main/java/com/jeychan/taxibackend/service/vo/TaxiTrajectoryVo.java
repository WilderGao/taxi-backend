package com.jeychan.taxibackend.service.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author WilderGao
 * time 2020-03-14 20:55
 * motto : everything is no in vain
 * description 出租车轨迹数据
 */
@Data
public class TaxiTrajectoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "VendorID", index = 0)
    private int vendorId;

    @ExcelProperty(value = "pickup_datetime", index = 1)
    private String pickupDatetime;

    @ExcelProperty(value = "dropoff_datetime", index = 2)
    private String dropOffDatetime;

    @ExcelProperty(value = "passenger_count", index = 3)
    private int passengerCount;

    @ExcelProperty(value = "trip_distance", index = 4)
    private double tripDistance;

    @ExcelProperty(value = "pickup_longitude", index = 5)
    private double pickupLongitude;

    @ExcelProperty(value = "pickup_latitude", index = 6)
    private double pickupLatitude;

    @ExcelProperty(value = "RatecodeID", index = 7)
    private int rateCodeId;

    @ExcelProperty(value = "store_and_fwd_flag", index = 8)
    private String storeAndFwdFlag;

    @ExcelProperty(value = "dropoff_longitude", index = 9)
    private double dropOffLongitude;

    @ExcelProperty(value = "dropoff_latitude", index = 10)
    private double dropOffLatitude;

    @ExcelProperty(value = "payment_type", index = 11)
    private int paymentType;

    @ExcelProperty(value = "fare_amount", index = 12)
    private double fareAmount;

    @ExcelProperty(value = "extra", index = 13)
    private double extra;

    @ExcelProperty(value = "mta_tax", index = 14)
    private double mtaTax;

    @ExcelProperty(value = "tip_amount", index = 15)
    private double tipAmount;

    @ExcelProperty(value = "tolls_amount", index = 16)
    private double tollsAmount;

    @ExcelProperty(value = "improvement_surcharge", index = 17)
    private double improvementSurcharge;

    @ExcelProperty(value = "total_amount", index = 18)
    private double totalAmount;
}
