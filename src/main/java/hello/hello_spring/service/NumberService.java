package hello.hello_spring.service;

import hello.hello_spring.domain.Number;
import hello.hello_spring.repository.NumberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

    private final NumberRepository numberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NumberService(NumberRepository numberRepository, SimpMessagingTemplate messagingTemplate) {
        this.numberRepository = numberRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Number add() {
        long current = count();

        Number number = new Number();
        number.setCount(current + 1);
        number.setCreatedAt(LocalDateTime.now());

        Number saved = numberRepository.save(number);

        broadcastTotal(saved.getCount());
        return saved;
    }

    public Number minus() {
        long current = count();

        Number number = new Number();
        number.setCount(current - 1);
        number.setCreatedAt(LocalDateTime.now());

        Number saved = numberRepository.save(number);

        broadcastTotal(saved.getCount());
        return saved;
    }

    public Long count() {
        List<Number> numbers = numberRepository.findAll();
        if (numbers.isEmpty()) return 0L;
        return numbers.get(numbers.size() - 1).getCount();
    }

    public void clearAll() {
        numberRepository.clearStore();
        broadcastTotal(0L);
    }

    private void broadcastTotal(Long total) {
        System.out.println("SEND /topic/total => " + total);
        messagingTemplate.convertAndSend("/topic/total", Map.of("total", total));
    }

    // 나머지 find... 메서드는 기존 그대로
    public Optional<Number> findById(Long id) { return numberRepository.findById(id); }
    public Optional<Number> findByCount(Long count) { return numberRepository.findByCount(count); }
    public Optional<Number> findByTotal() { return numberRepository.findByTotal(); }
    public List<Number> findNumbers() { return numberRepository.findAll(); }
}
