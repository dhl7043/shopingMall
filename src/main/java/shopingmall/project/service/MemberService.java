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

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void updateMember(Long id, String name, int age, String phoneNumber, String email, String city, String street, String zipcode) {
        Optional<Member> member = memberRepository.findById(id);
        member.get().builder()
                .name(name)
                .age(age)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(new Address(city, street, zipcode))
                .build();
        memberJpaRepository.updateMember(member);
    }

    private void duplicateCheckMember(Member member) {
        List<MemberCreate> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
