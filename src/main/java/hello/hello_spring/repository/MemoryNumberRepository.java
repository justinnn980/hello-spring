package hello.hello_spring.repository;

import hello.hello_spring.domain.Number;
import java.time.LocalDateTime;
import java.util.*;

public class MemoryNumberRepository implements NumberRepository {

    private static final Map<Long, Number> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Number save(Number number) {
        number.setId(++sequence);

        // createdAt이 비어 있으면 저장 시점으로 설정
        if (number.getCreatedAt() == null) {
            number.setCreatedAt(LocalDateTime.now());
        }

        store.put(number.getId(), number);
        return number;
    }

    @Override
    public Optional<Number> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Number> findByCount(Long count) {
        return store.values().stream()
            .filter(num -> num.getCount() == count)
            .findAny();
    }

    @Override
    public Optional<Number> findByCreatedAt(LocalDateTime createdAt) {
        return store.values().stream()
            .filter(num -> Objects.equals(num.getCreatedAt(), createdAt))
            .findAny();
    }

    @Override
    public List<Number> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
        sequence = 0L;
    }
}
