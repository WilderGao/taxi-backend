package com.jeychan.taxibackend.dao.entity;

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
public class Trip implements Serializable {

    private static final long serialVersionUID=1L;

    private LocalDateTime pickupDate;

    private LocalDateTime dropoffDate;

    private Integer passengerNum;

    private Double tripDistance;

    private Double avgSpeed;

    private Integer paymentType;

    private Double totalAmount;


}
