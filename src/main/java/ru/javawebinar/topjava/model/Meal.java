package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class Meal {
    private static int counter;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;
    private Integer uuid;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.uuid = inc();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
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
