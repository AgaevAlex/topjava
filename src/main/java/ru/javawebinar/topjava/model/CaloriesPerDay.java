package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class CaloriesPerDay {
    private int totalCalories;
    private final AtomicBoolean isExcess;

    public CaloriesPerDay(int totalCalories, AtomicBoolean isExcess) {
        this.totalCalories = totalCalories;
        this.isExcess = isExcess;
    }

    public AtomicBoolean getIsExcess() {
        return isExcess;
    }

    public void addCalories(int calories, int caloriesPerDay) {
        totalCalories += calories;
        isExcess.set(totalCalories > caloriesPerDay);
    }
}
