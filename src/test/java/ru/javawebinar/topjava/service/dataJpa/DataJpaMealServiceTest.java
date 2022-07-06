package ru.javawebinar.topjava.service.dataJpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void testGetMealWithUser() {
        Meal adminMeal = service.getMealWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(adminMeal, adminMeal1);
    }
}
