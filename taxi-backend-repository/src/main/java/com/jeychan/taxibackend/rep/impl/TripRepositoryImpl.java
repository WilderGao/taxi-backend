package com.jeychan.taxibackend.rep.impl;

import com.jeychan.taxibackend.dao.entity.Trip;
import com.jeychan.taxibackend.dao.mapper.TripMapper;
import com.jeychan.taxibackend.rep.service.ITripRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WilderGao
 * @since 2020-03-18
 */
@Repository
public class TripRepositoryImpl extends ServiceImpl<TripMapper, Trip> implements ITripRepository {

}
