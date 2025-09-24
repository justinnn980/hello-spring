package hello.hello_spring.controller;

import hello.hello_spring.domain.Number;
import hello.hello_spring.service.NumberService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/numbers")
public class NumberController {

    private final NumberService numberService;

    @Autowired
    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    // 새로운 Number 추가 (count 자동 증가)
    @PostMapping
    public Number addNumber() {
        Number number = new Number();
        numberService.add(number);
        return number; // 저장된 number 객체를 JSON으로 반환
    }

    // id로 조회
    @GetMapping("/{id}")
    public Optional<Number> getById(@PathVariable Long id) {
        return numberService.findById(id);
    }

    // count 값으로 조회
    @GetMapping("/count/{count}")
    public Optional<Number> getByCount(@PathVariable Long count) {
        return numberService.findByCount(count);
    }

    // 전체 Number 조회
    @GetMapping("/numberAll")
    public List<Number> getAll() {
        return numberService.findNumbers();
    }

    @DeleteMapping("/deleteall")
    public Map<String, String> clearAll() {
        numberService.clearAll();
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "All numbers deleted");
        return response; // JSON 반환
    }

}
