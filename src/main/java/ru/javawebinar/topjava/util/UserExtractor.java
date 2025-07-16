package ru.javawebinar.topjava.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserExtractor implements ResultSetExtractor<User> {

    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = null;
        Set<Role> roles = new HashSet<>();

        while (rs.next()) {
            if (user == null) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setRegistered(rs.getTimestamp("registered"));
            }

            String roleStr = rs.getString("role");
            if (roleStr != null) {
                roles.add(Role.valueOf(roleStr));
            }
        }
        if (user != null) {
            user.setRoles(roles);
        }
        return user;
    }
}
