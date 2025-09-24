package hello.hello_spring.controller;

import hello.hello_spring.domain.Number;
import hello.hello_spring.service.NumberService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        Number number = new Number(); // 일반 객체
        numberService.add(number);
        return number;
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

    @GetMapping("/total")
    public ResponseEntity<Number> getTotal() {
        Optional<Number> maxCountNumber = numberService.findByTotal();
        return maxCountNumber
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.noContent().build());
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
