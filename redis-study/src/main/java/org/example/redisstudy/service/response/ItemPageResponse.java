package org.example.redisstudy.service.response;

import java.util.List;
import org.example.redisstudy.model.Item;

public record ItemPageResponse(
        List<ItemResponse> items,
        long count
) {

    public static ItemPageResponse fromResponse(List<ItemResponse> items, long count) {
        return new ItemPageResponse(items, count);
    }

    public static ItemPageResponse from(List<Item> items, long count) {
        return fromResponse(items.stream().map(ItemResponse::from).toList(), count);
    }

    public ItemResponse last() {
        return items.get(items.size() - 1);
    }

}
