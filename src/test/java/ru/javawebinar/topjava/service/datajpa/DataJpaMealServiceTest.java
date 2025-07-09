package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(REPOSITORY_IMPLEMENTATION)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithMeals() {
        Meal actual = mealService.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
        USER_MATCHER.assertMatch(actual.getUser(), UserTestData.admin);
    }

    @Test(expected = NotFoundException.class)
    public void getWithMealsNotFound() {
        mealService.getWithUser(NOT_FOUND, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWithMealsNotOwn() {
        mealService.getWithUser(GUEST_ID, ADMIN_ID);
    }
}
