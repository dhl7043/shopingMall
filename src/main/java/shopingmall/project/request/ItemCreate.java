package shopingmall.project.request;

import lombok.Builder;
import lombok.Getter;
import shopingmall.project.type.ItemType;

@Getter
public class ItemCreate {

    private String name;
    private int price;
    private ItemType itemType;
    private String description;
    private int stockQuantity;

    @Builder
    public ItemCreate(String name, int price, ItemType itemType, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }
}
