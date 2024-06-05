package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.CaloriesPerDay;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MealCollector implements Collector<UserMeal, List<UserMealWithExcess>, List<UserMealWithExcess>> {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int caloriesPerDay;

    public MealCollector(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesPerDay = caloriesPerDay;
    }

    private final Map<LocalDate, CaloriesPerDay> caloriesByDate = new HashMap<>();

    @Override
    public Supplier<List<UserMealWithExcess>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<UserMealWithExcess>, UserMeal> accumulator() {
        return (list, meal)
                -> {
            CaloriesPerDay caloriesInfo = caloriesByDate.computeIfAbsent(meal.getDateTime().toLocalDate(),
                    k -> new CaloriesPerDay(0, new AtomicBoolean(false)));
            caloriesInfo.addCalories(meal.getCalories(), caloriesPerDay);

            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesInfo.getIsExcess()));
            }
        };
    }

    @Override
    public BinaryOperator<List<UserMealWithExcess>> combiner() {
        return (list1, list2) ->
        {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<UserMealWithExcess>, List<UserMealWithExcess>> finisher() {
        return Collections::unmodifiableList;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}