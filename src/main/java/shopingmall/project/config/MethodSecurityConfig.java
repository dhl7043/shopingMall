package shopingmall.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.repository.OrderRepository;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new ItemRegiPermissionEvaluator(itemRepository));
        handler.setPermissionEvaluator(new OrderRegiPermissionEvaluator(orderRepository));

        return handler;
    }
}
