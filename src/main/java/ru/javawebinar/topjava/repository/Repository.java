package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Repository {
    void add(Meal meal);

    void delete(int id);

    Meal get(int id);

    List<Meal> getAll();

    void update(int id, Meal meal);

}
