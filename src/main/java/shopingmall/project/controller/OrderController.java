package shopingmall.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import shopingmall.project.dto.request.OrderSearch;
import shopingmall.project.dto.response.OrderResponse;
import shopingmall.project.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /* 주문하기 */
    @PostMapping("/order")
    public void order(@RequestParam Long memberId,
                      @RequestParam Long itemId,
                      @RequestParam int count) {
        orderService.order(memberId, itemId, count);
    }

    /* 주문 취소 */
    @PostMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    /* 주문 조회 */
    @GetMapping("/orders")
    public Page<OrderResponse> orders(OrderSearch condition, Pageable pageable) {
        return orderService.findOrders(condition, pageable);
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderService.findByOrderId(orderId);
    }

    /* 주문 내역 */
}
