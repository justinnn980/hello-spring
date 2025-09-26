package hello.hello_spring.repository;

import hello.hello_spring.domain.Number;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcNumberRepository implements NumberRepository {

    private final DataSource dataSource;

    public JdbcNumberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Number save(Number number) {
        String sql = "INSERT INTO records(count, created_at) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setLong(1, number.getCount());
            pstmt.setTimestamp(2, Timestamp.valueOf(number.getCreatedAt()));

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                number.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return number;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Number> findById(Long id) {
        String sql = "SELECT * FROM records WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToNumber(rs));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Number> findByCount(Long count) {
        String sql = "SELECT * FROM records WHERE count = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, count);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToNumber(rs));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Number> findByCreatedAt(LocalDateTime createdAt) {
        String sql = "SELECT * FROM records WHERE created_at = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, Timestamp.valueOf(createdAt));

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToNumber(rs));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Number> findByTotal() {
        String sql = "SELECT * FROM records ORDER BY count DESC LIMIT 1";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToNumber(rs));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Number> findAll() {
        String sql = "SELECT * FROM records";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Number> numbers = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                numbers.add(mapRowToNumber(rs));
            }
            return numbers;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void clearStore() {
        String sql = "DELETE FROM records";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    private Number mapRowToNumber(ResultSet rs) throws SQLException {
        Number number = new Number();
        number.setId(rs.getLong("id"));
        number.setCount(rs.getLong("count"));
        number.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return number;
    }

    private Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
