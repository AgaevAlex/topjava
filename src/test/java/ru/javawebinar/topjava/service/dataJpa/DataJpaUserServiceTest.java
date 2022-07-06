package ru.javawebinar.topjava.service.dataJpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void testGetUserWithMeals() {
        User adminUser = service.getUserWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(adminUser, admin);
    }
}
