package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("jdbc")
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MealTestData.userMeal1);
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 31),
                USER_ID);
        MealTestData.assertMatch(meals, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        MealTestData.assertMatch(meals, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), GUEST_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, GUEST_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicate = new Meal(userMeal1);
        duplicate.setId(null);
        assertThrows(DataAccessException.class, () ->
                service.create(duplicate, USER_ID));
    }

    @Test
    public void getNotOwner() {
        assertThrows(NotFoundException.class, () ->
                service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void updateNotOwner() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () ->
                service.update(updated, ADMIN_ID));
    }

    @Test
    public void deleteNotOwner() {
        assertThrows(NotFoundException.class, () ->
                service.delete(MEAL1_ID, ADMIN_ID));
    }

}