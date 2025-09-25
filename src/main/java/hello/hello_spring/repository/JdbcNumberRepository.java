package hello.hello_spring.repository;

import static java.sql.DriverManager.getConnection;

import hello.hello_spring.domain.Number;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class JdbcNumberRepository implements NumberRepository{

    private final DataSource dataSource;

    public JdbcNumberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Number save(Number number) {
        String sql = "insert into numbers (number, created_at, updated_at) values (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

//        try {
//            conn = getConnection();
//            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            pstmt.setString(1, number.getId());
//
//            pstmt.executeUpdate();
//            rs = pstmt.getGeneratedKeys();
//
//            if(rs.next()) {
//                number.setId(rs.getLong(1));
//            } else {
//                throw new SQLException("id 조회 실패");
//            }
//            return number;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return number;
    }

    @Override
    public Optional<Number> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Number> findByCount(Long count) {
        return Optional.empty();
    }

    @Override
    public Optional<Number> findByCreatedAt(LocalDateTime createdAt) {
        return Optional.empty();
    }

    @Override
    public Optional<Number> findByTotal() {
        return Optional.empty();
    }

    @Override
    public List<Number> findAll() {
        return List.of();
    }

    @Override
    public void clearStore() {

    }
}
