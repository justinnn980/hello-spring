package hello.hello_spring.domain;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Number {
    private long id;
    private long count;       // count 값
    private LocalDateTime createdAt; // TIMESTAMP 매핑

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
