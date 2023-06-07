package shopingmall.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopingmall.project.dto.request.ItemSearchCondition;
import shopingmall.project.dto.response.ItemResponse;

public interface ItemRepositoryCustom {

    Page<ItemResponse> itemSearchPageComplex(ItemSearchCondition condition, Pageable pageable);
}
