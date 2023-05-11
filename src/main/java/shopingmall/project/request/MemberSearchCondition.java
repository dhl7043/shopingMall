package shopingmall.project.request;

import lombok.Data;
import lombok.Getter;

/**
 * 회원명, 나이
 */
@Getter
public class MemberSearchCondition {

    private String username;
    private Integer ageGoe;
    private Integer ageLoe;
}
