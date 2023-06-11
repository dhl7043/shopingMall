package shopingmall.project.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopingmall.project.dto.request.OrderSearch;
import shopingmall.project.dto.response.OrderResponse;
import shopingmall.project.dto.response.QItemOrderDto;
import shopingmall.project.dto.response.QOrderResponse;
import shopingmall.project.entity.shoping.QOrder;
import shopingmall.project.entity.shoping.QOrderItem;

import static shopingmall.project.entity.shoping.QOrder.*;
import static shopingmall.project.entity.shoping.QOrderItem.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderResponse> orderSearchPageComplex(OrderSearch condition, Pageable pageable) {
        queryFactory
                .select(new QOrderResponse(
                        order.id.as("orderId"),
                        order.member.id.as("memberId"),
                        order.member.name,
                        order.member.phoneNumber,
                        order.member.address.city,
                        order.member.address.street,
                        order.member.address.zipcode,
                        JPAExpressions
                                .select(new QItemOrderDto(
                                        orderItem.item.id,
                                        orderItem.item.name,
                                        orderItem.item.price,
                                        orderItem.item.itemType,
                                        orderItem.item.description,
                                        orderItem.orderPrice,
                                        orderItem.count
                                ))
                                .from(orderItem)
                                .where(orderItem.order.eq(order))
                                .fetch(),
                        order.delivery,
                        order.orderDate,
                        order.status))
                .from(order)
                .fetch();
        return null;
    }
}
