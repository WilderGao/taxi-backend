package com.jeychan.taxibackend.rep.impl;

import com.jeychan.taxibackend.dao.entity.TripDropoff;
import com.jeychan.taxibackend.dao.mapper.TripDropoffMapper;
import com.jeychan.taxibackend.rep.service.ITripDropoffRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WilderGao
 * @since 2020-03-18
 */
@Repository
public class TripDropoffRepositoryImpl extends ServiceImpl<TripDropoffMapper, TripDropoff> implements ITripDropoffRepository {

}
