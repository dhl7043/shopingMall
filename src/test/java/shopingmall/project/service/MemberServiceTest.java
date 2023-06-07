package shopingmall.project.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Address;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.repository.MemberJpaRepository;
import shopingmall.project.repository.MemberRepository;
import shopingmall.project.dto.request.MemberCreate;
import shopingmall.project.dto.request.MemberEdit;
import shopingmall.project.dto.request.MemberSearchCondition;
import shopingmall.project.dto.response.MemberResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Long memberId = createMember();

        assertEquals(1L, memberRepository.count());
        List<Member> memberList = memberRepository.findAll();
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);

        assertThat(memberList).contains(member);
        assertEquals("회원1", memberList.get(0).getName());
        assertEquals(10, memberList.get(0).getAge());
    }

    @Test
    @DisplayName("회원 1명 조회")
    void findMember() {
        Long memberId = createMember();

        MemberResponse result = memberService.getMember(memberId);

        assertNotNull(result);
        assertEquals(1L, memberRepository.count());
        assertEquals("회원1", result.getName());
        assertEquals(10, result.getAge());

    }

    @Test
    @DisplayName("회원정보 수정")
    void updateMember() {
        Long memberId = createMember();

        MemberEdit memberEdit = MemberEdit.builder()
                .name("회원2")
                .age(20)
                .phoneNumber("01001001001")
                .address(new Address("도시", "스트릿", "10101"))
                .build();

        memberService.updateMember(memberId, memberEdit);

        // then
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        assertEquals("회원2", member.getName());
        assertEquals(20, member.getAge());
        assertEquals("도시", member.getAddress().getCity());
    }

    @Test
    @DisplayName("회원 검색")
    void searchMember() {
        // given
        createMembers();

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(10);
        condition.setAgeLoe(20);

        PageRequest pageRequest = PageRequest.of(0, 2);
        // when
        Page<MemberResponse> results = memberService.searchMembers(condition, pageRequest);

        // then
        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("회원1", "회원2");
    }


    /**
     * 멤버 생성
     */
    private Long createMember() {
        MemberCreate memberCreate = Member.createMember("회원1", 10, "01012345678", "asd@asd.com", new Address("서울", "거리", "01010"));

        return memberService.join(memberCreate);
    }

    private void createMembers() {
        MemberCreate memberCreate1 = Member.createMember("회원1", 10, "01012345678", "asd@asd.com", new Address("서울", "거리", "01010"));
        MemberCreate memberCreate2 = Member.createMember("회원2", 20, "01043218765", "test2@test.com", new Address("경기도", "거리2", "01234"));
        MemberCreate memberCreate3 = Member.createMember("회원3", 30, "01012457812", "email@naver.com", new Address("인천", "거리3", "54321"));
        MemberCreate memberCreate4 = Member.createMember("회원4", 40, "01012457812", "email@kakao.com", new Address("서울", "거리4", "12345"));
        MemberCreate memberCreate5 = Member.createMember("회원5", 50, "01012457812", "email@daum.com", new Address("부산", "거리5", "67895"));

        memberService.join(memberCreate1);
        memberService.join(memberCreate2);
        memberService.join(memberCreate3);
        memberService.join(memberCreate4);
        memberService.join(memberCreate5);
    }
}