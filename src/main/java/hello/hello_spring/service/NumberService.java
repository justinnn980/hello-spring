package hello.hello_spring.service;

import hello.hello_spring.domain.Number;
import hello.hello_spring.repository.NumberRepository;
import hello.hello_spring.repository.MemoryNumberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class NumberService {

    private final NumberRepository numberRepository = new MemoryNumberRepository();

    /**
     * 숫자 증가 (저장)
     */
    public Long add(Number number) {
        // count가 비어있으면 자동으로 1 증가시켜 저장
        long nextCount = count() + 1;
        number.setCount(nextCount);

        // createdAt 자동 설정
        number.setCreatedAt(LocalDateTime.now());

        Number saved = numberRepository.save(number);
        return saved.getId();
    }

    /**
     * 현재 저장된 가장 최신 count 반환
     */
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
}
