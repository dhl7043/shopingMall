package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.dto.MemberDto;
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
    MemberService memberService;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void updateMember() {
        Member member = createMember();

        Member member1 = memberRepository.findById(member.getId()).orElse(null);
        assertThat(member1).isEqualTo(member);

        member1.changeMember("회원2", 20, "01001001001", "qwe@qwe.com", new Address("도시", "스트릿", "01012"));
        memberJpaRepository.updateMember(Optional.of(member1));

        Optional<Member> afterChangeMember = memberRepository.findById(member.getId());
        Member changedMember = afterChangeMember.orElse(null);
        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList).contains(changedMember);
        assertThat(memberList).extracting("age").containsExactly(20);
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