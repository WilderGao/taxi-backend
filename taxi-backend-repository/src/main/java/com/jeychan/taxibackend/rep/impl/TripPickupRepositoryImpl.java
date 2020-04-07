package com.jeychan.taxibackend.rep.impl;

import com.jeychan.taxibackend.dao.entity.TripPickup;
import com.jeychan.taxibackend.dao.mapper.TripPickupMapper;
import com.jeychan.taxibackend.rep.service.ITripPickupRepository;
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
public class TripPickupRepositoryImpl extends ServiceImpl<TripPickupMapper, TripPickup> implements ITripPickupRepository {


}
