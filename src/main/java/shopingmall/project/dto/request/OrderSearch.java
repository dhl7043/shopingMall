package shopingmall.project.dto.request;

import lombok.Getter;
import shopingmall.project.type.OrderType;

@Getter
public class OrderSearch {

    private String memberName; // 회원 이름
    private String email; // 회원 아이디(이메일)
    private OrderType orderStatus;

}
