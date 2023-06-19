package shopingmall.project.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import shopingmall.project.dto.request.OrderSearch;
import shopingmall.project.dto.response.OrderResponse;
import shopingmall.project.dto.response.QOrderResponse;
import shopingmall.project.entity.shoping.*;
import shopingmall.project.type.OrderType;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static shopingmall.project.entity.shoping.QOrder.*;
import static shopingmall.project.entity.shoping.QOrderItem.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderResponse> orderSearchPageComplex(OrderSearch condition, Pageable pageable) {

        List<OrderResponse> content = queryFactory
                .select(new QOrderResponse(
                        order.id.as("orderId"),
                        order.member.id.as("memberId"),
                        order.member.name.as("memberName"),
                        order.member.phoneNumber,
                        order.member.address.city,
                        order.member.address.street,
                        order.member.address.zipcode,
                        orderItem.item.id.as("itemId"),
                        orderItem.item.name.as("itemName"),
                        orderItem.item.price.as("itemPrice"),
                        order.totalPrice,
                        order.delivery,
                        order.orderDate,
                        order.status.as("orderStatus")))
                .from(order)
                .leftJoin(order.orderItems, orderItem)
                .where(orderIdEq(condition.getOrderId()),
                        memberIdEq(condition.getMemberId()),
                        itemIdEq(condition.getItemId()),
                        itemNameEq(condition.getItemName()),
                        orderDateGoe(condition.getOrderDateGoe()),
                        orderDateLoe(condition.getOrderDateLoe()),
                        orderStatusEq(condition.getOrderStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Tuple> countQuery = queryFactory
                .select(order, orderItem)
                .from(order)
                .leftJoin(order.orderItems, orderItem)
                .where(orderIdEq(condition.getOrderId()),
                        memberIdEq(condition.getMemberId()),
                        itemIdEq(condition.getItemId()),
                        itemNameEq(condition.getItemName()),
                        orderDateGoe(condition.getOrderDateGoe()),
                        orderDateLoe(condition.getOrderDateLoe()),
                        orderStatusEq(condition.getOrderStatus()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * 검색조건 orderId(주문번호), memberId(회원), itemId(상품), itemName(상품명), 날짜조회, 주문상태
     */
    private BooleanExpression orderIdEq(Long orderId) {
        return orderId != null ? order.id.eq(orderId) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? order.member.id.eq(memberId) : null;
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return itemId != null ? orderItem.item.id.eq(itemId) : null;
    }

    private BooleanExpression itemNameEq(String itemName) {
        return hasText(itemName) ? orderItem.item.name.eq(itemName) : null;
    }

    private BooleanExpression orderDateGoe(LocalDateTime orderDateGoe) {
        return orderDateGoe != null ? order.orderDate.goe(orderDateGoe) : null;
    }

    private BooleanExpression orderDateLoe(LocalDateTime orderDateLoe) {
        return orderDateLoe != null ? order.orderDate.loe(orderDateLoe) : null;
    }

    private BooleanExpression orderStatusEq(OrderType orderType) {
        return orderType != null ? order.status.eq(orderType) : null;
    }
}
