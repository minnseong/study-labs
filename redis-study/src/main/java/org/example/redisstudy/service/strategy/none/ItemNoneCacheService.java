package org.example.redisstudy.service.strategy.none;

import lombok.RequiredArgsConstructor;
import org.example.redisstudy.common.cache.CacheStrategy;
import org.example.redisstudy.common.cache.CustomCacheEvict;
import org.example.redisstudy.common.cache.CustomCachePut;
import org.example.redisstudy.common.cache.CustomCacheable;
import org.example.redisstudy.model.ItemCreateRequest;
import org.example.redisstudy.model.ItemUpdateRequest;
import org.example.redisstudy.service.ItemCacheService;
import org.example.redisstudy.service.ItemService;
import org.example.redisstudy.service.response.ItemPageResponse;
import org.example.redisstudy.service.response.ItemResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemNoneCacheService implements ItemCacheService {

    private final ItemService itemService;

    @Override
    @CustomCacheable(
            cacheStrategy = CacheStrategy.NONE,
            cacheName = "item",
            key = "#itemId",
            ttlSeconds = 5
    )
    public ItemResponse read(Long itemId) {
        return itemService.read(itemId);
    }

    @Override
    @CustomCacheable(
            cacheStrategy = CacheStrategy.NONE,
            cacheName = "itemList",
            key = "#page + ':' + #pageSize",
            ttlSeconds = 5
    )
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    @CustomCacheable(
            cacheStrategy = CacheStrategy.NONE,
            cacheName = "itemListInfiniteScroll",
            key = "#lastItemId + ':' + #pageSize",
            ttlSeconds = 5
    )
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    @Override
    public ItemResponse create(ItemCreateRequest request) {
        return itemService.create(request);
    }

    @Override
    @CustomCachePut(
            cacheStrategy = CacheStrategy.NONE,
            cacheName = "item",
            key = "#itemId",
            ttlSeconds = 5
    )
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @CustomCacheEvict(
            cacheStrategy = CacheStrategy.NONE,
            cacheName = "item",
            key = "#itemId"
    )
    @Override
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return CacheStrategy.NONE == cacheStrategy;
    }
}
