package ru.javawebinar.topjava.optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.caloriesPerDay;
import static ru.javawebinar.topjava.util.MealsUtil.createMealsList;

public class CollectionStorage<T> implements Storage<T> {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private final List<Meal> storage = new ArrayList<>(createMealsList());

    // TODO: добавить return после изменения List на Map
    @Override
    public void create(T meal) {
        log.info("Create" + meal);
        storage.add((Meal) meal);
    }

    public T read(int id) {
        log.info("Get " + id);
        return (T) storage.get(getSearchKey(id));
    }

    // TODO: добавить return после изменения List на Map
    @Override
    public void update(Object meal) {
        log.info("Update" + meal);
        Meal m = (Meal) meal;
        storage.set(getSearchKey(m.getid()), (Meal) meal);
    }

    @Override
    public void delete(int id) {
        log.info("Delete" + id);
        storage.remove(id);
    }

    protected int getSearchKey(int id) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getid().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public List<T> getList() {
        return (List<T>) MealsUtil.filteredByStreamsList(storage, caloriesPerDay, (m) -> true);
    }
}
