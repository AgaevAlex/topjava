package ru.javawebinar.topjava.optional;

import java.util.List;

public interface Storage<T> {
    void create(T t);

    T read(int id);

    void update(T t);

    void delete(int id);

    List<T> getList();
}
