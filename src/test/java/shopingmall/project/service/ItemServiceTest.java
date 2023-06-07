package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.dto.request.ItemSearchCondition;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.exception.NotFoundItemException;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.dto.request.ItemCreate;
import shopingmall.project.dto.request.ItemEdit;
import shopingmall.project.dto.response.ItemResponse;
import shopingmall.project.type.ItemType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void clean() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록")
    void saveItem() {
        Long itemId = createItem();

        // then
        assertEquals(1L, itemRepository.count());
        Item item = itemRepository.findById(itemId).orElseThrow(NotFoundItemException::new);
        assertEquals("상품1", item.getName());
        assertEquals(1000, item.getPrice());
        assertEquals(ItemType.CLOTHES, item.getItemType());
    }

    @Test
    @DisplayName("상품 수정")
    void updateItem() {
        // given
        Long itemId = createItem();

        // when
        ItemEdit itemEdit = ItemEdit.builder()
                .name("새로운 상품")
                .price(2000)
                .itemType(ItemType.CLOTHES)
                .description("설명")
                .stockQuantity(20)
                .build();

        itemService.updateItem(itemId, itemEdit);

        // then
        assertEquals(1L, itemRepository.count());
        Item item = itemRepository.findById(itemId).orElseThrow(NotFoundItemException::new);
        assertEquals("새로운 상품", item.getName());
        assertEquals(2000, item.getPrice());
        assertEquals(ItemType.CLOTHES, item.getItemType());
    }

    @Test
    @DisplayName("아이템 1개 조회")
    void findOneItem() {
        // given
        Long itemId = createItem();

        // when
        ItemResponse result = itemService.findByItemId(itemId);

        // then
        assertEquals("상품1", result.getName());
        assertEquals(1000, result.getPrice());
        Item item = itemRepository.findById(itemId).orElseThrow(NotFoundItemException::new);
        assertEquals(ItemType.CLOTHES, item.getItemType());
    }

    @Test
    @DisplayName("아이템 검색")
    void itemName() {
        // given
        createItems();

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setItemType(ItemType.CLOTHES);

        PageRequest pageRequest = PageRequest.of(0, 2);
        // when
        Page<ItemResponse> results = itemService.searchItems(condition, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("상품1", "상품3");
    }


    /**
     * 상품 생성
     */
    private Long createItem() {
        ItemCreate itemCreate = Item.createItem("상품1", 1000, ItemType.CLOTHES, "설명입니다.", 10);

        return itemService.saveItem(itemCreate);
    }

    private void createItems() {
        ItemCreate itemCreate1 = Item.createItem("상품1", 1000, ItemType.CLOTHES, "옷입니다.", 10);
        ItemCreate itemCreate2 = Item.createItem("상품2", 2000, ItemType.FOOD, "음식입니다.", 20);
        ItemCreate itemCreate3 = Item.createItem("상품3", 3000, ItemType.CLOTHES, "옷입니다.", 30);
        ItemCreate itemCreate4 = Item.createItem("상품4", 4000, ItemType.FOOD, "음식입니다.", 40);
        ItemCreate itemCreate5 = Item.createItem("상품5", 5000, ItemType.CLOTHES, "옷입니다.", 50);

        itemService.saveItem(itemCreate1);
        itemService.saveItem(itemCreate2);
        itemService.saveItem(itemCreate3);
        itemService.saveItem(itemCreate4);
        itemService.saveItem(itemCreate5);
    }
}