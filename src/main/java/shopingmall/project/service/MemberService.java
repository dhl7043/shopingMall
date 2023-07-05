package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.entity.shoping.Address;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.exception.AlreadyExistsEmailException;
import shopingmall.project.exception.NotFoundMemberException;
import shopingmall.project.repository.MemberRepository;
import shopingmall.project.dto.request.MemberCreate;
import shopingmall.project.dto.request.MemberEdit;
import shopingmall.project.dto.request.MemberSearchCondition;
import shopingmall.project.dto.response.MemberResponse;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    public Long signup(MemberCreate memberCreate) {
        Optional<Member> memberOptional = memberRepository.findByEmail(memberCreate.getEmail());
        if (memberOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = passwordEncoder.encode(memberCreate.getPassword());

        Member member = Member.builder()
                .name(memberCreate.getName())
                .age(memberCreate.getAge())
                .phoneNumber(memberCreate.getPhoneNumber())
                .email(memberCreate.getEmail())
                .password(encryptedPassword)
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
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        return MemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .age(member.getAge())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .address(member.getAddress())
                .build();
    }

    /**
     * 회원검색 (조건조회 가능)
     */
    public Page<MemberResponse> searchMembers(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.memberSearchPageComplex(condition, pageable);
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    public void updateMember(Long id, MemberEdit memberEdit) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);

        member.changeMember(
                memberEdit.getName(),
                memberEdit.getAge(),
                member.getPhoneNumber(),
                new Address(
                        memberEdit.getAddress().getCity(),
                        memberEdit.getAddress().getStreet(),
                        memberEdit.getAddress().getZipcode()));

        memberRepository.save(member);
    }

    private void duplicateCheckMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
