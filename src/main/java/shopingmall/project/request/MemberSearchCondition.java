package shopingmall.project.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 검색조건 - 유저명, 폰번호, email, 나이
 */
@Getter
@Setter
public class MemberSearchCondition {

    private String username;
    private Integer ageGoe;
    private Integer ageLoe;
    private String phoneNumber;
    private String email;
}
