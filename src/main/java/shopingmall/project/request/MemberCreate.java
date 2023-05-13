package shopingmall.project.request;

import lombok.Builder;
import lombok.Getter;
import shopingmall.project.entity.shoping.Address;

@Getter
public class MemberCreate {

    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private Address address;

    @Builder
    public MemberCreate(String name, int age, String phoneNumber, String email, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
