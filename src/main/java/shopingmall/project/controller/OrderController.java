package shopingmall.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    /* 주문 내역 */
}
