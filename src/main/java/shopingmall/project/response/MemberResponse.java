package shopingmall.project.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import shopingmall.project.entity.shoping.Address;

@Getter
public class MemberResponse {

    private Long memberId;
    private String username;
    private int age;
    private String phoneNumber;
    private String email;
    private Address address;


    @QueryProjection
    public MemberResponse(Long memberId, String username, int age, String phoneNumber, String email, Address address) {
        this.memberId = memberId;
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
