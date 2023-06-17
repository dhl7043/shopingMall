package shopingmall.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import shopingmall.project.entity.shoping.Delivery;
import shopingmall.project.type.OrderType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class OrderResponse {

    private Long orderId;
    /* Member */
    private Long memberId;
    private String memberName;
    private String phoneNumber;
    /* Address */
    private String city;
    private String street;
    private String zipcode;
    /* ItemOrder */
    private Long itemId;
    private String itemName;
    private int itemPrice;
    /* Order */
    private int totalPrice;
    private Delivery delivery;
    private LocalDateTime orderDate;
    private OrderType orderStatus;

    @QueryProjection
    @Builder
    public OrderResponse(Long orderId, Long memberId, String memberName, String phoneNumber, String city, String street, String zipcode, Long itemId, String itemName, int itemPrice, int totalPrice, Delivery delivery, LocalDateTime orderDate, OrderType orderStatus) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
