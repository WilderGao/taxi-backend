package com.jeychan.taxibackend.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author WilderGao
 * @since 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("collection_hour")
@Builder
public class CollectionHour implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDate date;

    private Integer hour;

    private Double totalAmount;

    private Double avgSpeed;

    private Integer passengerSum;

    private Integer orderSum;


    public static final String DATE = "date";
    public static final String HOUR = "hour";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String AVG_SPEED = "avgSpeed";
    public static final String PASSENGER_SUM = "passengerSum";
    public static final String ORDER_SUM = "orderSum";
}
