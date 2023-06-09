package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopingmall.project.dto.response.OrderResponse;
import shopingmall.project.type.DeliveryType;
import shopingmall.project.type.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 회원은 여러 주문이 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 하나의 주문은 하나의 배송정보
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderType status;

    private int totalPrice;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.enableOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order(member, delivery, OrderType.ORDER, LocalDateTime.now());

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryType.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.status = OrderType.CANCEL;

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    /**
     * 단건 조회
     */
    public OrderResponse findOneOrder() {
        return OrderResponse.builder()
                .orderId(id)
                .memberId(member.getId())
                .memberName(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .itemId(orderItems.get(0).getItem().getId())
                .itemName(orderItems.get(0).getItem().getName())
                .itemPrice(orderItems.get(0).getItem().getPrice())
                .totalPrice(totalPrice)
                .delivery(delivery)
                .orderDate(orderDate)
                .orderStatus(status)
                .build();
    }

    public Order(Member member, Delivery delivery, OrderType status, LocalDateTime orderDate) {
        this.member = member;
        this.delivery = delivery;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = getTotalPrice(); // totalPrice 필드 초기화
    }
}
