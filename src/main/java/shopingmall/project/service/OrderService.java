package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.dto.request.OrderSearch;
import shopingmall.project.dto.response.OrderResponse;
import shopingmall.project.entity.shoping.*;
import shopingmall.project.exception.NotFoundItemException;
import shopingmall.project.exception.NotFoundMemberException;
import shopingmall.project.exception.NotFoundOrderException;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.repository.MemberRepository;
import shopingmall.project.repository.OrderRepository;
import shopingmall.project.type.DeliveryType;

import java.util.List;
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

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundItemException::new);

        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .status(DeliveryType.READY)
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);

        order.cancel();
    }

    /**
     * 주문 조회
     */
    public Page<OrderResponse> findOrders(OrderSearch orderSearch, Pageable pageable) {
        return orderRepository.orderSearchPageComplex(orderSearch, pageable);
    }

    /**
     * 주문 상세 조회(단건)
     */
    public OrderResponse findByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundOrderException::new);

        return order.findOneOrder();
    }
}
