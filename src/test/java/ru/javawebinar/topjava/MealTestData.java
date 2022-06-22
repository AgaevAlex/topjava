package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal userMeal1 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal adminMeal2 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(100007, LocalDateTime.of(2021, Month.JANUARY, 30, 15, 0), "Обед", 500);
    public static final Meal userMeal4 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);

    public static final Meal userMeal6 = new Meal(100010, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);

    public static final Meal userMeal7 = new Meal(100011, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 10, 10, 0), "Завтрак", 333);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static <T> void assertMatch(Iterable<Meal> actual, List<T> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
