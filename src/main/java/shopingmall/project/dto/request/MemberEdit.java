package shopingmall.project.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import shopingmall.project.entity.shoping.Address;

/**
 * 회원 정보 수정
 * email -> id 수정불가
 */

@Getter
@ToString
public class MemberEdit {

    private String name;
    private int age;
    private String phoneNumber;
    private Address address;

    @Builder
    public MemberEdit(String name, int age, String phoneNumber, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
