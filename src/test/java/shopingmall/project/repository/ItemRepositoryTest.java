package shopingmall.project.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.type.ItemType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;

    //String name, int price, ItemType item, String description, int stockQuantity, List<Category> categories
    @Test
    public void itemName() {
        Item item1 = new Item("item1", 1000, ItemType.FOOD, "아이템1", 100);
        Item item2 = new Item("item2", 2000, ItemType.CLOTHES, "아이템2", 50);

        em.persist(item1);
        em.persist(item2);

        List<Item> result = itemRepository.findByName("item1");
        assertThat(result).extracting("price").containsExactly(1000);
    }
}