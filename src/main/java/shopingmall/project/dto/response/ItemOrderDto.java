package shopingmall.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shopingmall.project.type.ItemType;

/**
 * orderResponse와 다른점 - 수량이 필요 없음
 */

@Getter
public class ItemOrderDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private ItemType itemType;
    private String description;

    @QueryProjection
    @Builder
    public ItemOrderDto(Long itemId, String itemName, int itemPrice, ItemType itemType, String description, int itemOrderPrice, int itemOrderCount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        this.description = description;
    }
}
