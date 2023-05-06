package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.type.ItemType;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findByItemId(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    public void updateItem(Long id, String name, int price, ItemType item, String description, int stockQuantity) {
        Optional<Item> changeItem = itemRepository.findById(id);
        changeItem.get().builder()
                .name(name)
                .price(price)
                .item(item)
                .description(description)
                .stockQuantity(stockQuantity).build();
    }
}
