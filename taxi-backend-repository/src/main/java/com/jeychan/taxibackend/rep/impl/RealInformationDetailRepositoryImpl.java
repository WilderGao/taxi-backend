package com.jeychan.taxibackend.rep.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeychan.taxibackend.dao.entity.RealInformationDetail;
import com.jeychan.taxibackend.dao.mapper.RealInformationDetailMapper;
import com.jeychan.taxibackend.rep.service.RealInformationDetailRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-07 14:43
 * motto : everything is no in vain
 * description
 */
@Repository
public class RealInformationDetailRepositoryImpl extends ServiceImpl<RealInformationDetailMapper, RealInformationDetail>
        implements RealInformationDetailRepository {

    @Resource
    private RealInformationDetailMapper realInformationDetailMapper;

    @Override
    @Cacheable(key = "#informationId", value = "details")
    public List<RealInformationDetail> getDetailsById(int informationId) {
        QueryWrapper<RealInformationDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RealInformationDetail.REAL_ID, informationId);

        return realInformationDetailMapper.selectList(queryWrapper);
    }
}
