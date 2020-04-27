package com.jeychan.taxibackend.rep.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeychan.taxibackend.dao.entity.RealInformation;
import com.jeychan.taxibackend.dao.mapper.RealInformationMapper;
import com.jeychan.taxibackend.rep.service.RealInformationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-07 14:47
 * motto : everything is no in vain
 * description
 */
@Repository
public class RealInformationRepositoryImpl extends ServiceImpl<RealInformationMapper, RealInformation>
        implements RealInformationRepository {

    @Resource
    private RealInformationMapper realInformationMapper;

    @Override
    public RealInformation queryInformationByDayHourMin(LocalDate date, int hour, int min) {
        QueryWrapper<RealInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RealInformation.START_DAY, date);
        queryWrapper.eq(RealInformation.START_HOUR, hour);
        queryWrapper.eq(RealInformation.START_MIN, min);

        return realInformationMapper.selectOne(queryWrapper);

    }

    @Override
    public List<RealInformation> queryInformationByDayHour(LocalDate date, int hour) {
        QueryWrapper<RealInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RealInformation.START_DAY, date);
        queryWrapper.eq(RealInformation.START_HOUR, hour);

        return realInformationMapper.selectList(queryWrapper);
    }
}
