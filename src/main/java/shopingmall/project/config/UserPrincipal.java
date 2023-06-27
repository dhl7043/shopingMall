package shopingmall.project.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import shopingmall.project.entity.shoping.Member;

import java.util.List;

/**
 * User 생성자를 받아서 Security에 정보를 전달함
 */
public class UserPrincipal extends User {

    private final Long userId;

    // role: 역할 -> 관리자, 사용자, 매니저
    // authority: 권한 -> 글쓰기, 글 읽기, 사용자 정지시키기

    public UserPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(),
                List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("WRITE")
            ));
        this.userId = member.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
