package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopingmall.project.entity.baseEntity.BaseEntity;
import shopingmall.project.exception.NotEnoughStockException;
import shopingmall.project.request.ItemCreate;
import shopingmall.project.type.ItemType;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
public class Item extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;            //seq
    private String name;        //상품명
    private int price;          //상품가격

    @Enumerated(EnumType.STRING)
    private ItemType itemType;      //상품종류
    private String description; //상품설명
    private int stockQuantity;  //상품수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("수량이 부족합니다.");
        }
        this.stockQuantity = restStock;
    }

    @Builder
    public Item(String name, int price, ItemType itemType, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

//    public ItemCreate changeItem(String name, int price, ItemType itemType, String description, int stockQuantity) {
//        return ItemCreate.builder()
//                .name(name)
//                .price(price)
//                .itemType(itemType)
//                .description(description)
//                .stockQuantity(stockQuantity)
//                .build();
//    }
}
