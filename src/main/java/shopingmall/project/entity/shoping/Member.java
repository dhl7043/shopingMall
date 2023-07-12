package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopingmall.project.dto.request.MemberCreate;

import java.util.ArrayList;
import java.util.List;

/**
 * 간단하게만... 추후에 회원쪽 할때 업데이트
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Item> items;

    @Builder
    public Member(String name, int age, String phoneNumber, String email, String password, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    /**
     * 생성메서드 회원가입
     */
    public static MemberCreate createMember(String name, int age, String phoneNumber, String email, String password, Address address) {
        return MemberCreate.builder()
                .name(name)
                .age(age)
                .phoneNumber(phoneNumber)
                .email(email)
                .password(password)
                .address(address)
                .build();
    }

    /**
     * 회원 정보 수정
     */
    public void changeMember(String name, int age, String phoneNumber, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
