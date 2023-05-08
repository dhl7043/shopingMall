package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.type.ItemType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("아이템 이름으로 검색")
    public void itemName() {
        Item item1 = new Item("item1", 1000, ItemType.FOOD, "아이템1", 100);
        Item item2 = new Item("item2", 2000, ItemType.CLOTHES, "아이템2", 50);

        em.persist(item1);
        em.persist(item2);

        List<Item> result = itemRepository.findByName("item1");
        assertThat(result).extracting("price").containsExactly(1000);
    }
}