package ru.javawebinar.topjava.optional;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface Storage {
    void create(Meal meal);

    Meal read(Integer uuid);

    void update(Meal meal);

    void delete(Integer uuid);

    List<MealTo> getListMeals();
}
