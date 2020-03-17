package com.jeychan.taxibackend.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("trip_dropoff")
public class TripDropoff implements Serializable {

    private static final long serialVersionUID=1L;

    private LocalDateTime dropoffDate;

    private String dropoffLongitude;

    private String dropoffLatitude;

    private Integer tripId;


}
