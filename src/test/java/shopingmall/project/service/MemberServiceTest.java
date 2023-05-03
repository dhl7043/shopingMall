package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Address;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.repository.MemberJpaRepository;
import shopingmall.project.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void updateMember() {
        Member member = createMember();

        Member member1 = memberRepository.findById(member.getId()).orElse(null);
        // 맞는지 확인
        assertThat(member1).isEqualTo(member);

        // 수정
        member1.changeMember("회원2", 20, "01012341234", "asd@asdqw.asd", new Address("도시1", "스트릿1", "12312"));
    }

    /**
     * 멤버생성 //String name, String age, String phoneNumber, String email, Address address
     */
    private Member createMember() {
        Member member = new Member("회원1", 10, "01012345678", "abc1@abc.com", new Address("서울", "송파", "01010"));
        em.persist(member);
        return member;
    }
}