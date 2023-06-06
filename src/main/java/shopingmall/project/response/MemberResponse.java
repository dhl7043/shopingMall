package shopingmall.project.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shopingmall.project.entity.shoping.Address;

@Getter
public class MemberResponse {

    private Long memberId;
    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private Address address;


    @QueryProjection
    @Builder
    public MemberResponse(Long memberId, String name, int age, String phoneNumber, String email, Address address) {
        this.memberId = memberId;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
