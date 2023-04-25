package shopingmall.project.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Address;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.entity.shoping.Order;
import shopingmall.project.service.OrderService;
import shopingmall.project.type.ItemType;
import shopingmall.project.type.OrderType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    /**
     * 주문 테스트
     */
    @Test
    public void productOrder() {
        Member member = createMember();
        Item item = createItem();
        int orderCount = 10;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Optional<Order> getOrder = orderRepository.findById(orderId);
        assertEquals(OrderType.ORDER, getOrder.get().getStatus());
        assertEquals(1, getOrder.get().getOrderItems().size());
        assertEquals(10000 * orderCount, getOrder.get().getTotalPrice());
        assertEquals(90, item.getStockQuantity());
    }

    /**
     * 아이템 생성 //String name, int price, ItemType item, String description, int stockQuantity
     */
    private Item createItem() {
        Item item = new Item("상품1", 10000, ItemType.CLOTHES, "상품1 입니다.", 100);
        em.persist(item);
        return item;
    }

    /**
     * 멤버생성 //String name, String age, String phoneNumber, String email, Address address
     */
    private Member createMember() {
        Member member = new Member("회원1", 10, "01012345678", "abc1@abc.com", new Address("서울", "송파", "01010"));
        em.persist(member);
        return member;
    }

}