package shopingmall.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.entity.shoping.QMember;

@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QMember qMember = QMember.member;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void updateMemberName(Member member) {

        JPAUpdateClause updateMember = queryFactory
                .update(qMember)
//                .set()
                .where(qMember.id.eq(member.getId()));

        updateMember.execute();
    }
}
