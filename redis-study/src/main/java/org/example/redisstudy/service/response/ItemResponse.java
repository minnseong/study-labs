package org.example.redisstudy.service.response;

import org.example.redisstudy.model.Item;

public record ItemResponse(
        Long itemId, String data
) {

    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getItemId(), item.getData());
    }
}
