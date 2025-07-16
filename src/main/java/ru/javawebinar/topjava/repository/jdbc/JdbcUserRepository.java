package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ListUserExtractor;
import ru.javawebinar.topjava.util.UserExtractor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;
    ;

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
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
// тут совсем не уверен, как будто зачищать все записи и обновлять их это странное решение,
// но с другой стороны пытаться сравнить какая именно роль изменилась как будто оверхед
            deleteRoles(user);
            insertRoles(user);
            namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource);
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id " +
                        "WHERE id=?", new UserExtractor(), id);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id WHERE email=?",
                new UserExtractor(), email);
    }

    @Override
    public List<User> getAll() {
        List<User> query = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id ORDER BY name, email", new ListUserExtractor());
        return query;
    }

    private void insertRoles(User u) {
        List<Role> roles = new ArrayList<>(u.getRoles());

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

    private void deleteRoles(User u) {
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", u.getId());
    }
}
