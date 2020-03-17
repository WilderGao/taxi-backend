package com.jeychan.taxibackend.common.csv;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @author WilderGao
 * time 2020-03-14 21:40
 * motto : everything is no in vain
 * description
 */
public class CsvUtil {

    /**
     * 异步操作读取csv
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @param listener 异步读取监听器
     */
    public static void readExcel(String path, String fileName, AnalysisEventListener listener, Class c) {
        String filePath = path + fileName;
        EasyExcel.read(filePath, c, listener).sheet().doRead();
    }
}
