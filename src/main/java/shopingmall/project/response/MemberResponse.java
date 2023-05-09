package shopingmall.project.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long memberId;
    private String username;
    private int age;
    private Long teamId;
    private String teamName;

    @QueryProjection
    public MemberResponse(Long memberId, String username, int age, Long teamId, String teamName) {
        this.memberId = memberId;
        this.username = username;
        this.age = age;
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
