package ru.cft.starterkit.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.starterkit.entity.Item;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.repository.implement.ItemRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item add(String title, String description, Long price, Date date_to_end, Long owner_id) {
        Item item = new Item(title, description, price, date_to_end, UUID.randomUUID());
        item.setOwnerId(owner_id);
        item.setStatus("Enabled");
        return itemRepository.add(item);
    }

    public Item get(Long id) throws ObjectNotFoundException {
        return itemRepository.get(id);
    }

    public Item update(Item item) throws ObjectNotFoundException{
        itemRepository.update(item);
        return item;
    }

    public void delete(Long id) throws ObjectNotFoundException {
        itemRepository.delete(id);
    }

    public List<Item> list(){
        return itemRepository.getList();
    }

    public List<Item> list(Long id){
        return itemRepository.getList(id);
    }
}
