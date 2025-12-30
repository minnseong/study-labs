package org.example.redisstudy.service.strategy.springcacheannotation;

import lombok.RequiredArgsConstructor;
import org.example.redisstudy.common.cache.CacheStrategy;
import org.example.redisstudy.model.ItemCreateRequest;
import org.example.redisstudy.model.ItemUpdateRequest;
import org.example.redisstudy.service.ItemCacheService;
import org.example.redisstudy.service.ItemService;
import org.example.redisstudy.service.response.ItemPageResponse;
import org.example.redisstudy.service.response.ItemResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemSpringCacheAnnotationCacheService implements ItemCacheService {

    private final ItemService itemService;

    @Override
    @Cacheable(cacheNames = "item", key = "#itemId")
    public ItemResponse read(Long itemId) {
        return itemService.read(itemId);
    }

    @Override
    @Cacheable(cacheNames = "itemList", key = "#page + ':' + #pageSize")
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    @Cacheable(cacheNames = "itemListInfiniteScroll", key = "#lastItemId + ':' + #pageSize")
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    /**
     * 생성 시점에 즉시 캐시를 갱신할 수 있으나, 즉시 접근되지 않는 데이터라면 조회 시점에 캐시를 만들어줘도 충분하다.
     */
    @Override
    public ItemResponse create(ItemCreateRequest request) {
        return itemService.create(request);
    }

    @Override
    @CachePut(cacheNames = "item", key = "#itemId")
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    @CacheEvict(cacheNames = "item", key = "#itemId")
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return CacheStrategy.SPRING_CACHE_ANNOTATION == cacheStrategy;
    }
}
