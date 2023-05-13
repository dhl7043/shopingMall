package shopingmall.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.request.MemberSearchCondition;
import shopingmall.project.response.MemberResponse;
import shopingmall.project.response.QMemberResponse;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static shopingmall.project.entity.shoping.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberResponse> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberResponse> content = queryFactory
                .select(new QMemberResponse(
                        member.id.as("memberId"),
                        member.name.as("username"),
                        member.age,
                        member.phoneNumber,
                        member.email,
                        member.address.city,
                        member.address.street,
                        member.address.zipcode))
                .from(member)
                .where(usernameEq(condition.getUsername()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .where(usernameEq(condition.getUsername()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.name.eq(username) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
}