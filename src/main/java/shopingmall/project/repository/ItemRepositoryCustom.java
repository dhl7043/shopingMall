package shopingmall.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopingmall.project.request.ItemSearchCondition;
import shopingmall.project.response.ItemResponse;

public interface ItemRepositoryCustom {

    Page<ItemResponse> itemSearchPageComplex(ItemSearchCondition condition, Pageable pageable);
}
