package shopingmall.project.request;

import lombok.Getter;
import lombok.Setter;
import shopingmall.project.type.ItemType;

/**
 * 상품 검색조건 - 상품명, 가격, 상품종류
 */
@Getter
@Setter
public class ItemSearchCondition {

    private String name;
    private Integer priceGoe;
    private Integer priceLeo;
    private ItemType itemType;
}
