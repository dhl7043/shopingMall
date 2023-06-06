package shopingmall.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopingmall.project.request.MemberSearchCondition;
import shopingmall.project.response.MemberResponse;

public interface MemberRepositoryCustom {

    Page<MemberResponse> memberSearchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
