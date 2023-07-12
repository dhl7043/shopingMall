package shopingmall.project.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import shopingmall.project.entity.shoping.Order;
import shopingmall.project.exception.NotFoundOrderException;
import shopingmall.project.repository.OrderRepository;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class OrderRegiPermissionEvaluator implements PermissionEvaluator {

    private final OrderRepository orderRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Order order = orderRepository.findById((Long) targetId)
                .orElseThrow(NotFoundOrderException::new);

        if (!order.getMember().getId().equals(userPrincipal.getUserId())) {
            log.error("[인가실패] 해당 사용자가 작성한 글이 아닙니다. targetId={}", targetId);
            return false;
        }

        return true;
    }
}
