package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ListUserExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException {
        Map<Integer, User> userMap = new LinkedHashMap<>();

        while (rs.next()) {
            int userId = rs.getInt("id");
            User user = userMap.get(userId);

            if (user == null) {
                user = new User();
                user.setId(userId);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setRegistered(rs.getTimestamp("registered"));
                user.setRoles(new HashSet<>());
                userMap.put(userId, user);
            }

            String roleStr = rs.getString("role");
            if (roleStr != null) {
                user.getRoles().add(Role.valueOf(roleStr));
            }
        }

        return new ArrayList<>(userMap.values());
    }
}
