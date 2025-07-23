package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final RowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private static final ResultSetExtractor<List<User>> LIST_USER_EXTRACTOR = rs -> {
        Map<Integer, User> userMap = new LinkedHashMap<>();

        while (rs.next()) {
            int userId = rs.getInt("id");
            User user = userMap.get(userId);
            ;
            if (user == null) {
                user = new User(Objects.requireNonNull(ROW_MAPPER.mapRow(rs, 0)));
                userMap.put(userId, user);
            }

            String roleStr = rs.getString("role");
            if (roleStr != null) {
                user.getRoles().add(Role.valueOf(roleStr));
            }
        }

        return new ArrayList<>(userMap.values());
    };

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            }
            deleteRoles(user);
            insertRoles(user);
        }

        return user;
    }

    private void deleteRoles(User u) {
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", u.getId());
    }

    private void insertRoles(User u) {
        List<Role> roles = new ArrayList<>(u.getRoles());
        if (!roles.isEmpty()) {
            jdbcTemplate.batchUpdate(
                    "INSERT INTO user_role (user_id, role) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, u.getId());
                            ps.setString(2, roles.get(i).name());
                        }

                        @Override
                        public int getBatchSize() {
                            return roles.size();
                        }
                    }
            );
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id " +
                        "WHERE id=?", LIST_USER_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id WHERE email=?",
                LIST_USER_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u " +
                "LEFT JOIN user_role ur ON u.id = ur.user_id ORDER BY name, email", LIST_USER_EXTRACTOR);
        return users;
    }
}