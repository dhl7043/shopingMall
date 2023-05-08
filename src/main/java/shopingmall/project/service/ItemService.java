package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.request.ItemCreate;
import shopingmall.project.response.ItemResponse;
import shopingmall.project.type.ItemType;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(ItemCreate itemCreate) {
        Item item = Item.builder()
                .name(itemCreate.getName())
                .price(itemCreate.getPrice())
                .itemType(itemCreate.getItemType())
                .description(itemCreate.getDescription())
                .stockQuantity(itemCreate.getStockQuantity())
                .build();
        itemRepository.save(item);
    }

    public ItemResponse findByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 아이템입니다."));

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .itemType(item.getItemType())
                .description(item.getDescription())
                .build();
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }



    @Transactional
    public void updateItem(Long id, String name, int price, ItemType itemType, String description, int stockQuantity) {
        Optional<Item> changeItem = itemRepository.findById(id);
        changeItem.get().builder()
                .name(name)
                .price(price)
                .itemType(itemType)
                .description(description)
                .stockQuantity(stockQuantity)
                .build();
        //TODO itemRepository.updateItem()
    }
}
