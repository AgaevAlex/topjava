package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealMapper implements RowMapper<Meal> {

    @Override
    public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meal meal = new Meal();
        meal.setId(rs.getInt("id"));
        meal.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
        meal.setDescription(rs.getString("description"));
        meal.setCalories(rs.getInt("calories"));
        return meal;
    }
}
