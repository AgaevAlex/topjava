package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryList implements Repository {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private static final Map<Integer, Meal> mealsMap = Collections.synchronizedMap(new HashMap<>());

    public MealRepositoryList() {
        MealsUtil.meals.forEach(meal ->
                mealsMap.put(meal.getId(), meal));
    }

    public void add(Meal meal) {
        mealsMap.put(meal.getId(), meal);
    }

    public void delete(int id) {
        mealsMap.remove(id);
    }

    public Meal get(int id) {
        return mealsMap.get(id);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }

    public void update(int id, Meal meal) {
        if (mealsMap.get(id) != null) {
            mealsMap.put(id, meal);
        }
    }

    public static int createID() {
        return idCounter.incrementAndGet();
    }

}
