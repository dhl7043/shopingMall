package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.*;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.repository.MemberRepository;
import shopingmall.project.repository.OrderRepository;
import shopingmall.project.type.DeliveryType;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 주문
     * 엔티티 조회 -> 배송 생성 -> 주문상품 생성 -> 주문 생성 -> 주문 저장
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Item> item = itemRepository.findById(itemId);

        Delivery delivery = new Delivery(member.get().getAddress(), DeliveryType.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item.get(), item.get().getPrice(), count);

        Order order = Order.createOrder(member.get(), delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }
}
