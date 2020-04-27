package com.jeychan.taxibackend.web.controller;

import com.jeychan.taxibackend.common.enums.BizErrorCode;
import com.jeychan.taxibackend.common.exception.TaxiBackendBizException;
import com.jeychan.taxibackend.service.service.OfflineService;
import com.jeychan.taxibackend.service.vo.offline.HistorySummaryVo;
import com.jeychan.taxibackend.web.domain.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 16:35
 * motto : everything is no in vain
 * description
 */
@RestController
@RequestMapping("/offline")
@CrossOrigin
public class OfflineController {

    @Autowired
    private OfflineService offlineService;

    @GetMapping("/history/{year}/{month}/{day}/{type}")
    public CommonResponse queryHistorySummary(@PathVariable("year") Integer year,
                                              @PathVariable("month") Integer month,
                                              @PathVariable("day") Integer day,
                                              @PathVariable("type") String type) {
        try {
            HistorySummaryVo historySummaryVo = offlineService.queryHistorySummary(year, month, day, type);
            if (historySummaryVo == null) {
                return CommonResponse.getByBizError(BizErrorCode.PARAMETER_ERROR);
            }
            return CommonResponse.success(historySummaryVo);
        } catch (TaxiBackendBizException ex) {
            return CommonResponse.getByBizError(ex.getBizErrorCode());
        }
    }
}
