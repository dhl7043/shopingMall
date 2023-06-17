package shopingmall.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shopingmall.project.type.ItemType;

/**
 * orderResponse와 다른점 - 수량이 필요 없음
 */

@Getter
public class OrderItemDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private ItemType itemType;
    private String description;

    @QueryProjection
    @Builder
    public OrderItemDto(Long itemId, String itemName, int itemPrice, ItemType itemType, String description) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        this.description = description;
    }
}
