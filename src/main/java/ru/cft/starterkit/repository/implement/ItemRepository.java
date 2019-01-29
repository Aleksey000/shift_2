package ru.cft.starterkit.repository.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.cft.starterkit.entity.Item;
import ru.cft.starterkit.exception.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    private static final Logger log = LoggerFactory.getLogger(SampleEntityRepositoryImpl.class);

    private final AtomicLong idCounter = new AtomicLong();

    private final Map<Long, Item> storage = new ConcurrentHashMap<>();

    public Item add(Item entity) {
        entity.setId(idCounter.incrementAndGet());
        storage.put(entity.getId(), entity);

        log.info("Added new Item to storage: {}", entity);
        return entity;
    }

    public Item get(Long id) throws ObjectNotFoundException {
        Item entity = storage.get(id);

        if (entity == null) {
            log.error("Failed to get item with id '{}' from storage", id);
            throw new ObjectNotFoundException(String.format("Item with id %s not found", id));
        }

        log.info("Returned item with id '{}' from storage: {}", id, entity);
        return entity;
    }

    public void delete(Long id) throws ObjectNotFoundException {
        Item entity = storage.get(id);

        if (entity == null) {
            log.error("Failed to delete item with id '{}' from storage", id);
            throw new ObjectNotFoundException(String.format("Item with id %s not found", id));
        }

        storage.remove(id);
        log.info("Delete item with {} from storage", id);
    }

    public Item update(Item item) throws ObjectNotFoundException {
        if (item == null) {
            throw new ObjectNotFoundException(String.format("Item not found"));
        }
        storage.put(item.getId(), item);

        log.info("Update item to storage: {}", item);
        return item;
    }

    public List<Item> getList(){
        ArrayList<Item> list = new ArrayList<Item>();
        for(Map.Entry<Long, Item> entry: storage.entrySet()) {
            Item item = entry.getValue();
            if (item.getStatus().equals("Enabled")) {
                list.add(item);
            }
        }

        return list;
    }

    public List<Item> getList(Long id){
        ArrayList<Item> list = new ArrayList<Item>();
        for(Map.Entry<Long, Item> entry: storage.entrySet()) {
            Item item = entry.getValue();
            if (item.getOwnerId() == id) {
                list.add(item);
            }
        }

        return list;
    }
}
