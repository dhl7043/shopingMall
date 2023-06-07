package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopingmall.project.entity.baseEntity.BaseEntity;
import shopingmall.project.exception.NotEnoughStockException;
import shopingmall.project.dto.request.ItemCreate;
import shopingmall.project.dto.response.ItemResponse;
import shopingmall.project.type.ItemType;


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

//    @ManyToMany(mappedBy = "items")
//    private List<Category> categories = new ArrayList<>();

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
            throw new NotEnoughStockException();
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

    //==생성 메서드==// new로 하는것보단 생성메서드를 사용하면 코드의 명확성과 유지보수성이 향상됨
    public static ItemCreate createItem(String name, int price, ItemType itemType, String description, int stockQuantity) {
        return ItemCreate.builder()
                .name(name)
                .price(price)
                .itemType(itemType)
                .description(description)
                .stockQuantity(stockQuantity)
                .build();
    }

    //==비즈니스 로직==//
    /**
     * 상품 수정
     */
    public void updateItem(String name, int price, ItemType itemType, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    /**
     * 단건 조회
     */
    public ItemResponse findOneItem() {
        return ItemResponse.builder()
                .itemId(id)
                .name(name)
                .price(price)
                .itemType(itemType)
                .description(description)
                .stockQuantity(stockQuantity)
                .build();
    }
}
