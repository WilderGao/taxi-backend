package com.jeychan.taxibackend.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeychan.taxibackend.dao.entity.RealInformationDetail;

import java.util.List;

/**
 * @author WilderGao
 * time 2020-04-07 14:42
 * motto : everything is no in vain
 * description
 */
public interface RealInformationDetailRepository extends IService<RealInformationDetail> {

    List<RealInformationDetail> getDetailsById(int informationId);
}
