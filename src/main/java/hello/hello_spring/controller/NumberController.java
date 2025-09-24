package hello.hello_spring.controller;

import hello.hello_spring.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NumberController {

    private final NumberService numberService;

    @Autowired
    /// Autowired 서비스와 연결하는 생성될때 service를 주입 DI
    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }
}
