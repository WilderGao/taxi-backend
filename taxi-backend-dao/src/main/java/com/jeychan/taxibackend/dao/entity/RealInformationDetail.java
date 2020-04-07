package com.jeychan.taxibackend.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("real_information_detail")
public class RealInformationDetail implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer orderNum;

    private String geohash;

    private Integer type;

    private Integer realId;

    public static final String ORDER_NUM = "order_num";
    public static final String GEOHASH = "geohash";
    public static final String TYPE = "type";
    public static final String REAL_ID = "real_id";

    public static final int PICK_UP = 1;
    public static final int DROP_OFF = 2;
}
