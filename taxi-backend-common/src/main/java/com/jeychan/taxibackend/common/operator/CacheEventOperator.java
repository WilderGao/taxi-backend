package com.jeychan.taxibackend.common.operator;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * @author WilderGao
 * time 2020-04-07 15:42
 * motto : everything is no in vain
 * description
 */
@Slf4j
public class CacheEventOperator implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        log.info("CacheEventOperator.onEvent: cache is not exist, query from database, key={}, oldValue={}, newValue={}",
                cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
