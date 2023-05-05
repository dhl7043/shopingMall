package shopingmall.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.entity.shoping.QMember;

import java.util.Optional;

@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QMember qMember = QMember.member;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public void updateMember(Optional<Member> member) {

        JPAUpdateClause updateMember = queryFactory
                .update(qMember)
                .set(qMember.name, member.get().getName())
                .set(qMember.age, member.get().getAge())
                .set(qMember.phoneNumber, member.get().getPhoneNumber())
                .set(qMember.email, member.get().getEmail())
                .set(qMember.address, member.get().getAddress())
                .where(qMember.id.eq(member.get().getId()));

        updateMember.execute();
    }

}
