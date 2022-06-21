package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
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
        assertMatch(service.get(meal1.getId(), USER_ID), meal1);
    }

    @Test
    public void delete() {
        service.delete(meal1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 20), LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertMatch(meals, meal1, meal4);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, meal3, meal1, meal4);
    }

    @Test
    public void update() {
        Meal updated = meal1;
        updated.setCalories(meal1.getCalories() - 100);
        service.update(updated, USER_ID);
        assertMatch(service.get(meal1.getId(), USER_ID), updated);
    }

    @Test
    public void create() {
        Meal creater = service.create(getNew(), USER_ID);
        Integer newId = creater.getId();
        //   Integer userId = service.getUserIdByMeal(newId);
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(creater, newMeal);
        //  assertThat(userId).isEqualTo(UserTestData.USER_ID);
    }

    @Test
    public void updateSomeoneElse() {
        assertThrows(NotFoundException.class, () -> service.update(meal2, USER_ID));
    }

    @Test
    public void deleteSomeoneElse() {
        assertThrows(NotFoundException.class, () -> service.delete(meal2.getId(), USER_ID));
    }

    @Test
    public void getSomeoneElse() {
        assertThrows(NotFoundException.class, () -> service.get(meal2.getId(), USER_ID));
    }
}