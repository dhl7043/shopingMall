package shopingmall.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopingmall.project.dto.request.OrderSearch;
import shopingmall.project.dto.response.OrderResponse;

public interface OrderRepositoryCustom {

    Page<OrderResponse> orderSearchPageComplex(OrderSearch condition, Pageable pageable);
}
