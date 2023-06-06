package shopingmall.project.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shopingmall.project.type.ItemType;

@Getter
public class ItemResponse {

    private Long itemId;
    private String name;
    private int price;
    private ItemType itemType;
    private String description;
    private int stockQuantity;

    @QueryProjection
    @Builder
    public ItemResponse(Long itemId, String name, int price, ItemType itemType, String description, int stockQuantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }
}
