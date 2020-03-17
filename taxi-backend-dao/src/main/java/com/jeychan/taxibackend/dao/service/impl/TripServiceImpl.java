package com.jeychan.taxibackend.dao.service.impl;

import com.jeychan.taxibackend.dao.entity.Trip;
import com.jeychan.taxibackend.dao.mapper.TripMapper;
import com.jeychan.taxibackend.dao.service.ITripService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WilderGao
 * @since 2020-03-17
 */
@Service
public class TripServiceImpl extends ServiceImpl<TripMapper, Trip> implements ITripService {

}
