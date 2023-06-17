package shopingmall.project.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shopingmall.project.dto.request.OrderSearch;
import shopingmall.project.dto.response.OrderResponse;
import shopingmall.project.dto.response.QOrderResponse;
import shopingmall.project.entity.shoping.*;

import java.util.List;

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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Tuple> countQuery = queryFactory
                .select(order, orderItem)
                .from(order)
                .leftJoin(order.orderItems, orderItem);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * 검색조건 orderId(주문번호), memberId(회원), itemId(상품), itemName(상품명), 날짜조회, 주문상태
     */
}
