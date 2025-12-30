package org.example.redisstudy.service.strategy.none;

import java.time.Duration;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.redisstudy.common.cache.CacheHandler;
import org.example.redisstudy.common.cache.CacheStrategy;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ItemNoneCacheHandler implements CacheHandler {

    @Override
    public <T> T fetch(String key, Duration ttl, Supplier<T> dataSourceSupplier, Class<T> clazz) {

        log.info("[ItemNoneCacheHandler.fetch] key={}", key);
        return dataSourceSupplier.get();
    }

    @Override
    public void put(String key, Duration ttl, Object value) {
        log.info("[ItemNoneCacheHandler.put] key={}", key);
    }

    @Override
    public void evict(String key) {
        log.info("[ItemNoneCacheHandler.evict] key={}", key);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return CacheStrategy.NONE == cacheStrategy;
    }
}
