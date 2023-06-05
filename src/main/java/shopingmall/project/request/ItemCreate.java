package shopingmall.project.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import shopingmall.project.type.ItemType;

@Getter
public class ItemCreate {

    @NotBlank(message = "상품명을 입력해주세요")
    private String name;
    @NotBlank(message = "가격을 입력해주세요")
    private int price;
    @NotBlank(message = "상품 종류를 선택해주세요")
    private ItemType itemType;
    @NotBlank(message = "상품 설명을 입력해주세요")
    private String description;
    @NotBlank(message = "상품 수량을 입력해주세요")
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
