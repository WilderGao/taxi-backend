package com.jeychan.taxibackend.common.csv;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * @author WilderGao
 * time 2020-03-14 20:55
 * motto : everything is no in vain
 * description 出租车轨迹数据
 */
@Data
public class TaxiTrajectory {
    @ExcelProperty(value = "VendorID")
    private String vendorId;

    @ExcelProperty(value = "tpep_pickup_datetime")
    private String tPepPickupDatetime;

    @ExcelProperty(value = "tpep_dropoff_datetime")
    private String tPepDropOffDatetime;

    @ExcelProperty(value = "passenger_count")
    private String passengerCount;

    @ExcelProperty(value = "trip_distance")
    private String tripDistance;

    @ExcelProperty(value = "pickup_longitude")
    private String pickupLongitude;

    @ExcelProperty(value = "pickup_latitude")
    private String pickupLatitude;

    @ExcelProperty(value = "RatecodeID")
    private String rateCodeId;

    @ExcelProperty(value = "store_and_fwd_flag")
    private String storeAndFwdFlag;

    @ExcelProperty(value = "dropoff_longitude")
    private String dropOffLongitude;

    @ExcelProperty(value = "dropoff_latitude")
    private String dropOffLatitude;

    @ExcelProperty(value = "payment_type")
    private String paymentType;

    @ExcelProperty(value = "fare_amount")
    private String fareAmount;

    @ExcelProperty(value = "extra")
    private String extra;

    @ExcelProperty(value = "mta_tax")
    private String mtaTax;

    @ExcelProperty(value = "tip_amount")
    private String tipAmount;

    @ExcelProperty(value = "tolls_amount")
    private String tollsAmount;

    @ExcelProperty(value = "improvement_surcharge")
    private String improvementSurcharge;

    @ExcelProperty(value = "total_amount")
    private String totalAmount;

}
