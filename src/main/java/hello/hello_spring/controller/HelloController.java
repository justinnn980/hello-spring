package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello World");
        return "hello"; ///정적콘텐츠
    }

    @GetMapping("hell-mvc")
    public String hellmvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template"; ///MVC와 템플릿 엔진
    }

    @GetMapping("hello-speing")
    @ResponseBody ///응답 body부분에 데이터를 직접넣어주겠다.
    public String helloSpeing(@RequestParam("name") String name) {
        return "hello-speing"+name;
    }

    ///데이터를 내놔라
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; ///객체를 전달 : 객체가 오면 HttpMessageConverter 가 동작
    }
    static class Hello{
        private String name; ///외부에서 사용하기 위해 getter setter tkdyd

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
