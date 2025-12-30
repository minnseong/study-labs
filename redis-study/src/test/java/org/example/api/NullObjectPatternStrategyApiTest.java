package org.example.api;

import org.example.redisstudy.common.cache.CacheStrategy;
import org.example.redisstudy.service.response.ItemResponse;
import org.junit.jupiter.api.Test;

public class NullObjectPatternStrategyApiTest {

    static final CacheStrategy CACHE_STRATEGY = CacheStrategy.NULL_OBJECT_PATTERN;

    @Test
    void readNullData() {
        for (int i = 0; i < 3; i++) {
            ItemResponse itemResponse = ItemApiTestUtils.read(CACHE_STRATEGY, 9999L);
            System.out.println("itemResponse = " + itemResponse);
        }
    }

}
