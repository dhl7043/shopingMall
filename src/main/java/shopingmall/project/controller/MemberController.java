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

    @PostMapping("/signin")
    public void signIn(@RequestBody @Valid MemberCreate memberCreate) {
        memberService.join(memberCreate);
    }
}
