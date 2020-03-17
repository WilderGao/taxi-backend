package com.jeychan.taxibackend.common.csv;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-03-14 21:28
 * motto : everything is no in vain
 * description
 */
@Slf4j
public class CsvAnalyseListenerOperator extends AnalysisEventListener<TaxiTrajectory> {
    private List<TaxiTrajectory> taxiTrajectories;
    private static final int BATCH_SIZE = 5000;

    @Override
    public void invoke(TaxiTrajectory taxiTrajectory, AnalysisContext analysisContext) {
        log.info("ExcelAnalyseListenerOperator.invoke: new data, taxiTrajectory={}", JSONObject.toJSONString(taxiTrajectory));
        taxiTrajectories.add(taxiTrajectory);

        if (taxiTrajectories.size() >= BATCH_SIZE) {
            doAction();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        doAction();
    }

    private void doAction() {
        //批量插入数据库
        log.info("ExcelAnalyseListenerOperator.doAction: insert datas to mysql...");


        taxiTrajectories.clear();
    }
}
