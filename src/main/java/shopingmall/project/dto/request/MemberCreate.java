package shopingmall.project.dto.request;

import lombok.*;
import shopingmall.project.entity.shoping.Address;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreate {

    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private String password;
    private Address address;

    @Builder
    public MemberCreate(String name, int age, String phoneNumber, String email, String password, Address address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
