package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.request.ItemCreate;
import shopingmall.project.request.ItemEdit;
import shopingmall.project.response.ItemResponse;
import shopingmall.project.type.ItemType;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품등록
     */
    @Transactional
    public Long saveItem(ItemCreate itemCreate) {
        Item item = Item.builder()
                .name(itemCreate.getName())
                .price(itemCreate.getPrice())
                .itemType(itemCreate.getItemType())
                .description(itemCreate.getDescription())
                .stockQuantity(itemCreate.getStockQuantity())
                .build();
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void updateItem(Long id, ItemEdit itemEdit) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 조회할 수 없습니다."));

        item.updateItem(itemEdit.getName(),
                itemEdit.getPrice(),
                itemEdit.getItemType(),
                itemEdit.getDescription(),
                itemEdit.getStockQuantity());

        itemRepository.save(item);
    }

    /**
     * 상품 단건 조회 (상품 하나 클릭시)
     */
    public ItemResponse findByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 아이템입니다."));

        return item.findOneItem();
    }

    /**
     * 상품 여러개 조회 (조건검색)
     */
}
