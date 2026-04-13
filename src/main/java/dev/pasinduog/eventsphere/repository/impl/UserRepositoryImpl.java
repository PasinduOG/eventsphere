package dev.pasinduog.eventsphere.repository.impl;

import dev.pasinduog.eventsphere.exception.UserEmailAlreadyExistsException;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper() {
        return ((rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setPasswordHash(rs.getString("password_hash"));
            user.setSkillsAndInterests(rs.getString("skills_and_interests"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return user;
        });
    }

    private RowMapper<User> customRowMapper() {
        return ((rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setSkillsAndInterests(rs.getString("skills_and_interests"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return user;
        });
    }

    @Override
    public boolean save(User user) {
        try {
            String sql = "INSERT INTO users (id, full_name, email, `role`, password_hash, skills_and_interests) VALUES (?,?,?,?,?,?)";
            return jdbcTemplate.update(sql,
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole(),
                    user.getPasswordHash(),
                    user.getSkillsAndInterests()) > 0;
        } catch (DuplicateKeyException e) {
            throw new UserEmailAlreadyExistsException(user.getEmail());
        }
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE users SET full_name = ?, skills_and_interests = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getFullName(), user.getSkillsAndInterests(), user.getId()) > 0;
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT id, full_name, email, `role`, password_hash, skills_and_interests, created_at FROM users WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper(), id).stream().findFirst();
    }

    @Override
    public List<User> findByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        String inSql = String.join(",", java.util.Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT id, full_name, email, `role`, password_hash, skills_and_interests, created_at FROM users WHERE id IN (" + inSql + ")";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, full_name, email, `role`, password_hash, skills_and_interests, created_at FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, rowMapper(), email).stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, full_name, email, `role`, skills_and_interests, created_at FROM users";
        return jdbcTemplate.query(sql, customRowMapper());
    }
}
