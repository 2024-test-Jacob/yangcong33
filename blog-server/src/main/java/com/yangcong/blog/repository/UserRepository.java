package com.yangcong.blog.repository;

import com.yangcong.blog.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private static final RowMapper<User> USER_ROW_MAPPER = UserRepository::mapUser;

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query("""
                SELECT id, username, password_hash, nickname, status, created_at, updated_at
                FROM users
                WHERE username = ?
                LIMIT 1
                """, USER_ROW_MAPPER, username);
        return users.stream().findFirst();
    }

    public Optional<User> findById(Long id) {
        List<User> users = jdbcTemplate.query("""
                SELECT id, username, password_hash, nickname, status, created_at, updated_at
                FROM users
                WHERE id = ?
                LIMIT 1
                """, USER_ROW_MAPPER, id);
        return users.stream().findFirst();
    }

    private static User mapUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password_hash"),
                resultSet.getString("nickname"),
                resultSet.getInt("status"),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                resultSet.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
