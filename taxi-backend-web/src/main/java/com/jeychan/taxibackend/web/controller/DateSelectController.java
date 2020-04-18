package com.jeychan.taxibackend.web.controller;

import com.jeychan.taxibackend.common.enums.BizErrorCode;
import com.jeychan.taxibackend.service.service.DateSelectService;
import com.jeychan.taxibackend.web.domain.CommonResponse;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-18 14:16
 * motto : everything is no in vain
 * description
 */
@RestController
@RequestMapping("/date/select")
public class DateSelectController {

    @Autowired
    private DateSelectService dateSelectService;

    @GetMapping("/year")
    public CommonResponse<List<Integer>> queryYears() {
        List<Integer> years = dateSelectService.queryViewableYear();
        return CommonResponse.success(years);
    }

    @GetMapping("/month/{year}")
    public CommonResponse<List<Integer>> queryMonths(@PathVariable("year") int year) {
        List<Integer> months = dateSelectService.queryViewableMonth(year);
        return packageResponse(months);
    }

    @GetMapping("/day/{year}/{month}")
    public CommonResponse<List<Integer>> queryDays(@PathVariable("year") int year, @PathVariable("month") int month) {
        List<Integer> days = dateSelectService.queryViewableDays(year, month);
        return packageResponse(days);
    }

    private CommonResponse<List<Integer>> packageResponse(List<Integer> result) {
        if (result == null || result.isEmpty()) {
            return new CommonResponse<>(BizErrorCode.PARAM_INVALID.getCode(), BizErrorCode.PARAM_INVALID.getDesc(), null);
        }
        return CommonResponse.success(result);
    }

}
