package org.example.redisstudy.service.strategy.nullobjectpattern;

import lombok.RequiredArgsConstructor;
import org.example.redisstudy.common.cache.CacheStrategy;
import org.example.redisstudy.model.ItemCreateRequest;
import org.example.redisstudy.model.ItemUpdateRequest;
import org.example.redisstudy.service.ItemCacheService;
import org.example.redisstudy.service.ItemService;
import org.example.redisstudy.service.response.ItemPageResponse;
import org.example.redisstudy.service.response.ItemResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemNullObjectPatternCacheService implements ItemCacheService {

    private final ItemService itemService;

    private static final ItemResponse nullObject = new ItemResponse(null, null);

    @Override
    @Cacheable(value = "item", key = "#itemId")
    public ItemResponse read(Long itemId) {
        ItemResponse itemResponse = itemService.read(itemId);

        if (itemResponse == null) {
            return nullObject;
        }

        return itemResponse;
    }

    @Override
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    @Override
    public ItemResponse create(ItemCreateRequest request) {
        return itemService.create(request);
    }

    @Override
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return cacheStrategy == CacheStrategy.NULL_OBJECT_PATTERN;
    }
}
