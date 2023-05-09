package shopingmall.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopingmall.project.request.MemberSearchCondition;
import shopingmall.project.response.MemberResponse;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberResponse> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        return null;
    }
}
