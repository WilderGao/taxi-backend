package com.jeychan.taxibackend.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
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
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("trip_dropoff")
@Builder
public class TripDropoff implements Serializable, Comparable<TripDropoff> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime dropoffDate;

    private double dropoffLongitude;

    private double dropoffLatitude;

    private Integer tripId;


    @Override
    public int compareTo(TripDropoff o) {
        return this.dropoffDate.compareTo(o.dropoffDate);
    }
}
