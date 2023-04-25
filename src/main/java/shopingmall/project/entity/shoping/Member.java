package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 간단하게만... 추후에 회원쪽 할때 업데이트
 */

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;
    private String phoneNumber;
    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    /**
     * 테스트용 멤버 생성
     */
    public Member(String name, int age, String phoneNumber, String email, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
