package hello.hello_spring.service;

import hello.hello_spring.domain.Number;
import hello.hello_spring.repository.MemoryNumberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class NumberServiceTest {

    // 테스트에서 사용할 저장소와 서비스
    MemoryNumberRepository repository = new MemoryNumberRepository();
    NumberService numberService = new NumberService(repository);

    @AfterEach
    void clearStore() {
        repository.clearStore(); // 매 테스트 후 저장소 초기화
    }

    @Test
    void add하면_count가_증가한다() {
        Number number1 = new Number();
        Long id1 = numberService.add(number1);

        Number number2 = new Number();
        Long id2 = numberService.add(number2);

        assertThat(id1).isEqualTo(1L);
        assertThat(id2).isEqualTo(2L);
        assertThat(numberService.count()).isEqualTo(2L);
    }

    @Test
    void 처음에는_count가_0이다() {
        assertThat(numberService.count()).isEqualTo(0L);
    }

    @Test
    void add하면_createdAt이_자동설정된다() {
        Number number = new Number();
        numberService.add(number);

        assertThat(number.getCreatedAt()).isNotNull();
    }

    @Test
    void findById로_저장된_객체를_조회할수있다() {
        Number number = new Number();
        Long id = numberService.add(number);

        Optional<Number> result = numberService.findById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
    }

    @Test
    void findByCount로_저장된_객체를_조회할수있다() {
        Number number = new Number();
        numberService.add(number); // count = 1

        Optional<Number> result = numberService.findByCount(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getCount()).isEqualTo(1L);
    }
}
