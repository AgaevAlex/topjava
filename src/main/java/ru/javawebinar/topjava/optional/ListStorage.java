package ru.javawebinar.topjava.optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.UserServlet;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.javawebinar.topjava.util.MealsUtil.caloriesPerDay;

public class ListStorage implements Storage {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);
    private final List<Meal> storage = new CopyOnWriteArrayList<>();

    @Override
    public void create(Meal meal) {
        log.info("Create" + meal);
        doSave(meal);
    }

    public Meal read(Integer uuid) {
        log.info("Get " + uuid);
        int searchKey = receiveExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void update(Meal meal) {
        log.info("Update" + meal);
        int searchKey = receiveNotExistingSearchKey(meal.getUuid());
        doUpdate(meal, searchKey);
    }

    @Override
    public void delete(Integer uuid) {
        log.info("Delete" + uuid);
        int searchKey = receiveExistingSearchKey(uuid);
        doRemove(searchKey);
    }

    public int receiveExistingSearchKey(Integer uuid) {
        int searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            log.info("Meal " + uuid + " not exist");
        }
        return searchKey;
    }

    public int receiveNotExistingSearchKey(Integer uuid) {
        int searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            log.info("Meal " + uuid + " already exist");
        }
        return searchKey;
    }

    protected Integer getSearchKey(Integer uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected void doSave(Meal meal) {
        storage.add(meal);
    }

    protected void doRemove(Integer index) {
        storage.remove(index.intValue());
    }

    protected Meal doGet(Integer index) {
        return storage.get(index);
    }

    protected void doUpdate(Meal meal, Integer index) {
        storage.set(index, meal);
        System.out.println("Success. Meal  " + storage.get(index) + " was updated");
    }

    public List<MealTo> getListMeals() {
        return MealsUtil.filteredByCycles(storage, LocalTime.of(0, 0), LocalTime.of(23, 59), caloriesPerDay);
    }
}
