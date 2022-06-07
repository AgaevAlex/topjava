//package ru.javawebinar.topjava.util;
//
//import ru.javawebinar.topjava.model.UserMeal;
//import ru.javawebinar.topjava.model.UserMealWithExcess;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.*;
//import java.util.function.BiConsumer;
//import java.util.function.BinaryOperator;
//import java.util.function.Function;
//import java.util.function.Supplier;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//
//public class MealsCollector implements Collector<UserMeal, Map<LocalDate, Integer>, List<UserMealWithExcess>> {
//    private final int caloriesPerDay;
//    private final LocalTime startTime;
//    private final LocalTime endTime;
//    List<UserMealWithExcess> meals = new ArrayList<>();
//
//    public MealsCollector(int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
//        this.caloriesPerDay = caloriesPerDay;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
//
//
//    @Override
//    public Supplier<Map<LocalDate, Integer>> supplier() {
//
//        return HashMap::new;
//    }
//
//    @Override
//    public BiConsumer<Map<LocalDate, Integer>, UserMeal> accumulator() {
//        return (o1, o2) -> {
//            meals.add(new UserMealWithExcess(o2.getDateTime(), o2.getDescription(), o2.getCalories(), false));
//            o1.merge(o2.getDateTime().toLocalDate(), o2.getCalories(), Integer::sum);
//
//        };
//    }
//
//    @Override
//    public BinaryOperator<Map<LocalDate, Integer>> combiner() {
//        return (l, r) -> {
//            l.putAll(r);
//            return l;
//        };
//    }
//
//    @Override
//    public Function<Map<LocalDate, Integer>, List<UserMealWithExcess>> finisher() {
//
//
//        return (l) -> meals.stream()
//                .peek(o -> o.setExcess(l.get(o.getDateTime().toLocalDate()) > caloriesPerDay))
//                .filter(e -> TimeUtil.isBetweenHalfOpen(e.getDateTime().toLocalTime(), startTime, endTime))
//                .collect(Collectors.toList());
//
//    }
//
//    @Override
//    public Set<Characteristics> characteristics() {
//        return Collections.emptySet();
//    }
//}
