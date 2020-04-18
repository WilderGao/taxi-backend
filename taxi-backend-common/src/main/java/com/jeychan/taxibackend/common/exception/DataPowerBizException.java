package com.jeychan.taxibackend.common.exception;

import com.jeychan.taxibackend.common.enums.BizErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 杨世谨
 */
@Getter
@Setter
public class DataPowerBizException extends DataPowerException {

    private BizErrorCode bizErrorCode;

    public DataPowerBizException(BizErrorCode bizErrorCode) {
        super(bizErrorCode.getDesc());
        this.bizErrorCode = bizErrorCode;
    }
}
