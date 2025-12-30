package org.example.redisstudy.common.cache;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheAspect {

    private final List<CacheHandler> cacheHandlers;
    private final CacheKeyGenerator cacheKeyGenerator;

    @Around("@annotation(customCacheable)")
    public Object handleCacheable(ProceedingJoinPoint joinPoint, CustomCacheable customCacheable) {
        CacheStrategy cacheStrategy = customCacheable.cacheStrategy();
        CacheHandler cacheHandler = findCacheHandler(cacheStrategy);

        String key = cacheKeyGenerator.genKey(joinPoint, cacheStrategy, customCacheable.cacheName(),
                customCacheable.key());
        Duration ttl = Duration.ofSeconds(customCacheable.ttlSeconds());
        Supplier<Object> dateSourceSupplier = createDateSourceSupplier(joinPoint);
        Class returnType = findReturnType(joinPoint);

        try {
            log.info("[CacheAspect.handleCacheable] key={}", key);
            return cacheHandler.fetch(
                    key,
                    ttl,
                    dateSourceSupplier,
                    returnType
            );
        } catch (Exception e) {
            log.error("[CacheAspect.handleCacheable] key={}", key, e);
            return dateSourceSupplier.get();
        }
    }

    @AfterReturning(pointcut = "@annotation(customCachePut)", returning = "result")
    public void handleCachePut(JoinPoint joinPoint, CustomCachePut customCachePut, Object result) {
        CacheStrategy cacheStrategy = customCachePut.cacheStrategy();
        CacheHandler cacheHandler = findCacheHandler(cacheStrategy);
        String key = cacheKeyGenerator.genKey(joinPoint, cacheStrategy, customCachePut.cacheName(), customCachePut.key());
        log.info("[CacheAspect.handleCachePut] key={}", key);
        cacheHandler.put(key, Duration.ofSeconds(customCachePut.ttlSeconds()), result);
    }


    @AfterReturning(pointcut = "@annotation(customCacheEvict)")
    public void handleCacheEvict(JoinPoint joinPoint, CustomCacheEvict customCacheEvict) {
        CacheStrategy cacheStrategy = customCacheEvict.cacheStrategy();
        CacheHandler cacheHandler = findCacheHandler(cacheStrategy);
        String key = cacheKeyGenerator.genKey(joinPoint, cacheStrategy, customCacheEvict.cacheName(), customCacheEvict.key());
        log.info("[CacheAspect.handleCacheEvict] key={}", key);
        cacheHandler.evict(key);
    }

    private CacheHandler findCacheHandler(CacheStrategy cacheStrategy) {
        return cacheHandlers.stream()
                .filter(handler -> handler.supports(cacheStrategy))
                .findFirst()
                .orElseThrow();
    }

    private Supplier<Object> createDateSourceSupplier(ProceedingJoinPoint joinPoint) {
        return () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        };
    }

    private Class findReturnType(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getReturnType();
    }
}
