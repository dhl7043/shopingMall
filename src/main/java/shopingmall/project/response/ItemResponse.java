package shopingmall.project.response;

import lombok.Builder;
import lombok.Getter;
import shopingmall.project.entity.shoping.Category;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.type.ItemType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final ItemType itemType;
    private final String description;
    private final List<Category> categories = new ArrayList<>();

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.itemType = item.getItemType();
        this.description = item.getDescription();
    }

    @Builder
    public ItemResponse(Long id, String name, int price, ItemType itemType, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.description = description;
    }
}
