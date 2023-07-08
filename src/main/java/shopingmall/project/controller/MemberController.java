package shopingmall.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopingmall.project.dto.request.MemberCreate;
import shopingmall.project.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/login")
    public String login() {
        return "로그인";
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody @Valid MemberCreate memberCreate) {
        memberService.signup(memberCreate);
    }
}
