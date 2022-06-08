package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public final static int caloriesPerDay = 2000;

    public static List<Meal> createMealsList() {
        List<Meal> meals = Arrays.asList(
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(idGenerator.inc(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        return meals;
    }

//    public static Map<Integer, Meal> createMealsMap() {
//        return createMealsList().stream().collect(Collectors.toMap((Meal meal) -> meal.getid(), Function.identity()));
//    }

    public static List<MealTo> filteredByStreamsList(List<Meal> meals, int caloriesPerDay, Predicate<Meal> predicate) {
        Map<LocalDate, Integer> caloriesEveryDay = meals.stream()
                .collect(Collectors
                        .groupingBy((Meal meal) -> meal.getDateTime().toLocalDate(), Collectors.summingInt(meal1 -> meal1.getCalories())));
        return meals.stream().filter(predicate)
                .map(m -> new MealTo(m.getid(), m.getDateTime(), m.getDescription(), m.getCalories(), caloriesEveryDay.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

//    public static List<MealTo> filteredByStreamsMap(Map<Integer, Meal> meals, int caloriesPerDay, Predicate<Meal> predicate) {
//        Map<LocalDate, Integer> caloriesEveryDay = meals.entrySet().stream()
//                .collect(Collectors.groupingBy(e -> {e.getValue().getDateTime().toLocalDate(), Collectors.summingInt(e.getValue().));
//    }
}
