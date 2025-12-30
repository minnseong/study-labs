package org.example.redisstudy.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import lombok.extern.slf4j.Slf4j;
import org.example.redisstudy.model.Item;
import org.springframework.stereotype.Repository;

/**
 * Data Source
 */

@Slf4j
@Repository
public class ItemRepository {

    private final ConcurrentSkipListMap<Long, Item> database = new ConcurrentSkipListMap<>();

    public Optional<Item> read(Long itemId) {
        log.info("[itemRepository.read] itemId = {}", itemId);
        return Optional.ofNullable(database.get(itemId));
    }

    public List<Item> readAll(Long page, Long pageSize) {
        log.info("[itemRepository.readAll] page = {}, pageSize = {}", page, pageSize);
        return database.values().stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<Item> readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        log.info("[itemRepository.readAllInfiniteScroll] lastItemId = {}, pageSize = {}", lastItemId, pageSize);
        if (lastItemId == null) {
            return database.values().stream()
                    .limit(pageSize)
                    .toList();
        }

        return database.tailMap(lastItemId, false).values().stream()
                .limit(pageSize)
                .toList();
    }

    public Item create(Item item) {
        database.put(item.getItemId(), item);
        return item;
    }

    public Item update(Item item) {
        database.put(item.getItemId(), item);
        return item;
    }

    public void delete(Item item) {
        database.remove(item.getItemId());
    }

    public long count() {
        return database.size();
    }
}
