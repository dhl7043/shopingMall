package shopingmall.project.dto.request;

import lombok.Getter;
import lombok.Setter;
import shopingmall.project.type.OrderType;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderSearch {

    private Long orderId;
    private Long memberId;
    private Long itemId;
    private String itemName;
    private LocalDateTime orderDateGoe;
    private LocalDateTime orderDateLoe;
    private OrderType orderStatus;
}
