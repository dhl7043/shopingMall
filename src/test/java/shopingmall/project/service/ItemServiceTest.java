package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.request.ItemCreate;
import shopingmall.project.request.ItemEdit;
import shopingmall.project.response.ItemResponse;
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
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException());
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
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException());
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
        ItemResponse findOneItem = itemService.findByItemId(itemId);

        // then
        assertEquals("상품1", findOneItem.getName());
        assertEquals(1000, findOneItem.getPrice());
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("조회 오류"));
        assertEquals(ItemType.CLOTHES, item.getItemType());
    }

    @Test
    @DisplayName("아이템 이름으로 검색")
    void itemName() {
        Item item1 = new Item("item1", 1000, ItemType.FOOD, "아이템1", 100);
        Item item2 = new Item("item2", 2000, ItemType.CLOTHES, "아이템2", 50);

        em.persist(item1);
        em.persist(item2);

        List<Item> result = itemRepository.findByName("item1");
        assertThat(result).extracting("price").containsExactly(1000);
    }


    private Long createItem() {
        ItemCreate itemCreate = Item.createItem("상품1", 1000, ItemType.CLOTHES, "설명입니다.", 10);

        return itemService.saveItem(itemCreate);
    }
}