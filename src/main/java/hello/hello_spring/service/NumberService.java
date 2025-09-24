package hello.hello_spring.service;

import hello.hello_spring.domain.Number;
import hello.hello_spring.repository.NumberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

    private final NumberRepository numberRepository;

    @Autowired
    public NumberService(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    public Long add(Number number) {
        long nextCount = count() + 1;
        number.setCount(nextCount);
        number.setCreatedAt(LocalDateTime.now());

        Number saved = numberRepository.save(number);
        return saved.getId();
    }

    public Long count() {
        List<Number> numbers = numberRepository.findAll();
        if (numbers.isEmpty()) {
            return 0L;
        }
        return numbers.get(numbers.size() - 1).getCount();
    }

    public Optional<Number> findById(Long id) {
        return numberRepository.findById(id);
    }

    public Optional<Number> findByCount(Long count) {
        return numberRepository.findByCount(count);
    }

    public Optional<Number> findByTotal() {
        return numberRepository.findByTotal();
    }

    // 전체 조회
    public List<Number> findNumbers() {
        return numberRepository.findAll();
    }

    public void clearAll() {
        numberRepository.clearStore();
    }
}
