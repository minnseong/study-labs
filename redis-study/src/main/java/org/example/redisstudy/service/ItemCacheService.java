package org.example.redisstudy.service;

import org.example.redisstudy.common.cache.CacheStrategy;
import org.example.redisstudy.model.ItemCreateRequest;
import org.example.redisstudy.model.ItemUpdateRequest;
import org.example.redisstudy.service.response.ItemPageResponse;
import org.example.redisstudy.service.response.ItemResponse;

public interface ItemCacheService {

    ItemResponse read(Long itemId);

    ItemPageResponse readAll(Long page, Long pageSize);

    ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize);

    ItemResponse create(ItemCreateRequest request);

    ItemResponse update(Long itemId, ItemUpdateRequest request);

    void delete(Long itemId);

    boolean supports(CacheStrategy cacheStrategy);

}
