package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.Getter;
import shopingmall.project.type.DeliveryType;
import shopingmall.project.type.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 회원은 여러 주문이 가능
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 하나의 주문은 하나의 배송정보
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderType status;

    //TODO 멤버와의 연관관계

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.enableOrder(this);
    }

    public void enableDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.enableOrder(this);
    }

    // TODO createOrder 멤버, 배송, 배송주문들

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryType.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        //TODO 전체 생성자에 캔슬추가
//        new Order(OrderType.CANCEL);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public Order(OrderType status) {
        this.status = status;
    }

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
