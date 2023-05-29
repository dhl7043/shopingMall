package shopingmall.project.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import shopingmall.project.type.ItemType;

@Getter
@ToString
public class ItemEdit {

    private String name;
    private int price;
    private ItemType itemType;
    private String description;
    private int stockQuantity;

    @Builder
    public ItemEdit(String name, int price, ItemType itemType, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }
}
