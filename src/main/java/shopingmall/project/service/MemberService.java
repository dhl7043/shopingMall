package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.dto.MemberDto;
import shopingmall.project.entity.shoping.Address;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.repository.MemberJpaRepository;
import shopingmall.project.repository.MemberRepository;
import shopingmall.project.request.MemberCreate;
import shopingmall.project.request.MemberEdit;
import shopingmall.project.response.MemberResponse;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 회원가입
     */
    public Long join(MemberCreate memberCreate) {
        Member member = Member.builder()
                .name(memberCreate.getName())
                .age(memberCreate.getAge())
                .phoneNumber(memberCreate.getPhoneNumber())
                .email(memberCreate.getEmail())
                .address(new Address(
                        memberCreate.getAddress().getCity(),
                        memberCreate.getAddress().getStreet(),
                        memberCreate.getAddress().getZipcode()))
                .build();


        duplicateCheckMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 멤버 단건조회
     */
    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);// Exception 만들기

        return MemberResponse.builder()
                .memberId(member.getId())
                .username(member.getName())
                .age(member.getAge())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .address(member.getAddress())
                .build();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    public void updateMember(Long id, MemberEdit memberEdit) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 조회할 수 없습니다."));

        member.changeMember(
                memberEdit.getName(),
                memberEdit.getAge(),
                member.getPhoneNumber(),
                member.getAddress());

        memberRepository.save(member);
    }

    private void duplicateCheckMember(Member member) {
        List<MemberCreate> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
