package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 Order와 상품 Item은 ManyToMany이다. (최대한 onetoone, manytomnay는 쓰지말자)
 * OrderItem을 통해 ManyToOne, OneToMany로 하자
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //ManyToOne에서는 LAZY를 사용해야하는데 manytomany중간지점도 LAZY?
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY) //ManyToOne에서는 LAZY를 사용해야하는데 manytomany중간지점도 LAZY?
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;     // 주문가격
    private int count;          // 주문수량

    /**
     * createOrderItem에서 필요한 생성자
     */
    public OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    /**
     * 주문오더들어오면 item에서 수량 제거
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem(item, orderPrice, count);

        item.removeStock(count);
        return orderItem;
    }

    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void enableOrder(Order order) {
        this.order = order;
    }
}
