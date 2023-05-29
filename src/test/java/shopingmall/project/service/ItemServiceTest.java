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
import shopingmall.project.type.ItemType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        createOneItem();

        // then
        Assertions.assertEquals(1L, itemRepository.count());
        Item item = itemRepository.findAll().get(0);
        Assertions.assertEquals("상품1", item.getName());
        Assertions.assertEquals(1000, item.getPrice());
        Assertions.assertEquals(ItemType.CLOTHES, item.getItemType());
    }

    @Test
    @DisplayName("상품 수정")
    void updateItem() {
        // given
        Item savedItem = Item.builder()
                .name("저장된 아이템")
                .price(2000)
                .stockQuantity(5)
                .description("설명")
                .itemType(ItemType.FOOD)
                .build();

        itemRepository.save(savedItem);



        // when

        // then

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


    private void createOneItem() {
        // given
        ItemCreate itemCreate = ItemCreate.builder()
                .name("상품1")
                .price(1000)
                .stockQuantity(10)
                .description("설명입니다.")
                .itemType(ItemType.CLOTHES)
                .build();

        // when
        itemService.saveItem(itemCreate);
    }
}