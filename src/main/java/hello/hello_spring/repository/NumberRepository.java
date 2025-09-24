package hello.hello_spring.repository;

import hello.hello_spring.domain.Number;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NumberRepository {
    Number save(Number number);
    Optional<Number> findById(Long id);
    Optional<Number> findByCount(Long count);
    Optional<Number> findByCreatedAt(LocalDateTime createdAt);
    List<Number> findAll();
    // 전체 삭제용 메서드 추가
    void clearStore();
}
