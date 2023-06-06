package shopingmall.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.request.ItemSearchCondition;
import shopingmall.project.response.ItemResponse;
import shopingmall.project.response.QItemResponse;
import shopingmall.project.type.ItemType;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static shopingmall.project.entity.shoping.QItem.*;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ItemResponse> itemSearchPageComplex(ItemSearchCondition condition, Pageable pageable) {
        List<ItemResponse> content = queryFactory
                .select(new QItemResponse(
                        item.id.as("itemId"),
                        item.name,
                        item.price,
                        item.itemType,
                        item.description,
                        item.stockQuantity))
                .from(item)
                .where(itemNameEq(condition.getName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLeo()),
                        itemTypeEq(condition.getItemType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> countQuery = queryFactory
                .select(item)
                .from(item)
                .where(itemNameEq(condition.getName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLeo()),
                        itemTypeEq(condition.getItemType()));


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * 검색조건
     */
    private BooleanExpression itemNameEq(String name) {
        return hasText(name) ? item.name.eq(name) : null;
    }

    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe != null ? item.price.goe(priceGoe) : null;
    }

    private BooleanExpression priceLoe(Integer priceLoe) {
        return priceLoe != null ? item.price.loe(priceLoe) : null;
    }

    private BooleanExpression itemTypeEq(ItemType itemType) {
        return itemType != null ? item.itemType.eq(itemType) : null;
    }
}
