package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller ///외부 요청을 받는다.
public class MemberController {

    private final MemberService memberService;

    @Autowired ///DI
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

}
