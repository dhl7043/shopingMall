package shopingmall.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shopingmall.project.entity.shoping.Member;

import java.util.Optional;

@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

//    QMember qMember = QMember.member;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void updateMemberName(Optional<Member> member) {

//        JPAUpdateClause updateMember = queryFactory
//                .update(qMember)
////                .set()
//                .where(qMember.id.eq(member.get().getId()));
//
//        updateMember.execute();
    }

}
