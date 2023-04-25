package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.entity.shoping.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
