package shopingmall.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.dto.request.MemberSearchCondition;
import shopingmall.project.dto.response.MemberResponse;
import shopingmall.project.dto.response.QMemberResponse;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static shopingmall.project.entity.shoping.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberResponse> memberSearchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberResponse> content = queryFactory
                .select(new QMemberResponse(
                        member.id.as("memberId"),
                        member.name.as("username"),
                        member.age,
                        member.phoneNumber,
                        member.email,
                        member.address))
                .from(member)
                .where(usernameEq(condition.getUsername()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()),
                        phoneNumberEq(condition.getEmail()),
                        emailEq(condition.getEmail()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .where(usernameEq(condition.getUsername()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()),
                        phoneNumberEq(condition.getPhoneNumber()),
                        emailEq(condition.getEmail()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * 검색조건
     */
    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.name.eq(username) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

    private BooleanExpression phoneNumberEq(String phoneNumber) {
        return hasText(phoneNumber) ? member.phoneNumber.eq(phoneNumber) : null;
    }

    private BooleanExpression emailEq(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }
}
