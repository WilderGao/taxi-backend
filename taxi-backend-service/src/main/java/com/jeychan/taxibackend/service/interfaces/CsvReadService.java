package com.jeychan.taxibackend.service.interfaces;

/**
 * @author WilderGao
 * time 2020-03-18 11:35
 * motto : everything is no in vain
 * description
 */
public interface CsvReadService {
    /**
     * 将csv的数据导入到数据库
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return 导入结果
     */
    int importCsvDataToDatabase(String fileName, String filePath);
}
