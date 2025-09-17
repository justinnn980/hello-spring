package hello.hello_spring.repository;

import hello.hello_spring.domain.Number;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryNumberRepositoryTest {

    MemoryNumberRepository repository = new MemoryNumberRepository();

    // 각 테스트가 끝날 때마다 저장소 초기화
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        Number number = new Number();
        number.setCount(5);

        Number savedNumber = repository.save(number);

        Number result = repository.findById(savedNumber.getId()).get();
        assertThat(result).isEqualTo(savedNumber);
    }

    @Test
    void findById() {
        Number number1 = new Number();
        number1.setCount(1);
        repository.save(number1);

        Number number2 = new Number();
        number2.setCount(2);
        repository.save(number2);

        Number result = repository.findById(number1.getId()).get();
        assertThat(result).isEqualTo(number1);
    }

    @Test
    void findByCount() {
        Number number1 = new Number();
        number1.setCount(10);
        repository.save(number1);

        Optional<Number> result = repository.findByCount(10L);
        assertThat(result).isPresent();
        assertThat(result.get().getCount()).isEqualTo(10);
    }

    @Test
    void findByCreatedAt() {
        Number number = new Number();
        number.setCount(100);
        number.setCreatedAt(LocalDateTime.of(2025, 9, 17, 12, 0));
        repository.save(number);

        Optional<Number> result = repository.findByCreatedAt(LocalDateTime.of(2025, 9, 17, 12, 0));
        assertThat(result).isPresent();
        assertThat(result.get().getCount()).isEqualTo(100);
    }

    @Test
    void findAll() {
        Number number1 = new Number();
        number1.setCount(1);
        repository.save(number1);

        Number number2 = new Number();
        number2.setCount(2);
        repository.save(number2);

        List<Number> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(number1, number2);
    }
}
