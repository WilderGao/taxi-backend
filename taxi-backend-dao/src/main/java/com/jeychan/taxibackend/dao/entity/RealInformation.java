package com.jeychan.taxibackend.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author WilderGao
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("real_information")
public class RealInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDate startDay;

    private Integer startHour;

    private Integer startMin;

    private LocalDate endDay;

    private Integer endHour;

    private Integer endMin;

    private Integer orderNum;

    private Integer passengerNum;

    private Double avgSpeed;

    private Double totalAmount;

    public final static String ID = "id";
    public final static String START_DAY = "start_day";
    public final static String START_HOUR = "start_hour";
    public final static String START_MIN = "start_min";
    public final static String END_DAY = "end_day";
    public final static String END_HOUR = "end_hour";
    public final static String END_MIN = "end_min";
}
