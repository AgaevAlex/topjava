package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.idGenerator;

import java.time.LocalDateTime;

public class Meal {
    private static int counter;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;
    private Integer id;

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.id = idGenerator.inc();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    private int inc() {
        synchronized (this) {
            return counter++;
        }
    }
}
