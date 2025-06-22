package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {

    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(idCounter.getAndIncrement());
           return meals.put(meal.getId(), meal);
        } else {
         return meals.computeIfPresent(meal.getId(),(key,value)-> meal);
        }
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    public Map<Integer, Meal> getMeals() {
        return meals;
    }

    @Override
    public Meal getMealById(int id) {
        return meals.get(id);
    }

    @Override
    public Collection<MealTo> getAllMeal() {
        return MealsUtil.filteredByStreams(meals.values(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }
}
