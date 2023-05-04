package shopingmall.project.dto;

import lombok.Getter;
import shopingmall.project.entity.shoping.Address;

@Getter
public class MemberDto {

    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private Address address;

    public MemberDto(String name, int age, String phoneNumber, String email, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
