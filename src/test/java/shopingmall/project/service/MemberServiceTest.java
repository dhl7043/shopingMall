package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.dto.MemberDto;
import shopingmall.project.entity.shoping.Address;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.repository.MemberJpaRepository;
import shopingmall.project.repository.MemberRepository;
import shopingmall.project.request.MemberCreate;

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

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원등록")
    void joinMember() {
        MemberCreate memberCreate = createMember();

        memberService.join(memberCreate);

        Assertions.assertEquals(1L, memberRepository.count());
    }

    @Test
    @DisplayName("회원정보 수정")
    public void updateMember() {
        MemberCreate memberCreate = createMember();

        memberService.join(memberCreate);
        /*
        Member member1 = memberRepository.findById(member.getId()).orElse(null);
        assertThat(member1).isEqualTo(member);

        member1.builder()
                .name("회원2")
                .age(20)
                .phoneNumber("01001001001")
                .email("qwe@qwe.com")
                .address(new Address("도시", "스트릿", "10101"))
                .build();

        memberJpaRepository.updateMember(Optional.of(member1));

        Optional<Member> afterChangeMember = memberRepository.findById(member.getId());
        Member changedMember = afterChangeMember.orElse(null);
        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList).contains(changedMember);
        assertThat(memberList).extracting("age").containsExactly(20);

         */
    }

    /**
     * 멤버생성 //String name, String age, String phoneNumber, String email, Address address
     */
    private MemberCreate createMember() {
        MemberCreate memberCreate = MemberCreate.builder()
                .name("회원1")
                .age(10)
                .phoneNumber("01012345678")
                .email("abc@asd.com")
                .address(new Address("서울", "거리", "번호"))
                .build();
        em.persist(memberCreate);
        return memberCreate;
    }
}