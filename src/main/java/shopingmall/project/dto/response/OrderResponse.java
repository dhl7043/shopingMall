package shopingmall.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shopingmall.project.entity.shoping.Order;
import shopingmall.project.entity.shoping.OrderItem;
import shopingmall.project.type.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponse {

    private Long orderId;
    private int totalPrice;
    /* Member */
    private Long memberId;
    private String memberName;
    private String phoneNumber;
    /* Address */
    private String city;
    private String street;
    private String zipcode;
    /* ItemOrder */
    private List<ItemOrderDto> items;
    private OrderType orderStatus;
    private LocalDateTime orderDate;

    @Builder
    @QueryProjection
    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.memberId = order.getMember().getId();
        this.memberName = order.getMember().getName();
        this.phoneNumber = order.getMember().getPhoneNumber();
        this.city = order.getMember().getAddress().getCity();
        this.street = order.getMember().getAddress().getStreet();
        this.zipcode = order.getMember().getAddress().getZipcode();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getOrderDate();

        List<ItemOrderDto> itemOrders = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {
            ItemOrderDto itemOrder = ItemOrderDto.builder()
                    .itemId(orderItem.getItem().getId())
                    .itemName(orderItem.getItem().getName())
                    .itemPrice(orderItem
                            .getItem().getPrice())
                    .itemType(orderItem.getItem().getItemType())
                    .description(orderItem.getItem().getDescription())
                    .build();

            itemOrders.add(itemOrder);
        }
        this.items = itemOrders;
    }
}
