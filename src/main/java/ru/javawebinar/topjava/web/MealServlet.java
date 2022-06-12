package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.optional.CollectionStorage;
import ru.javawebinar.topjava.optional.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.javawebinar.topjava.util.MealsUtil.caloriesPerDay;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static String INSERT_OR_EDIT = "/addEditMeals.jsp";
    private static String LIST_MEALS = "/listMeals.jsp";
    private Storage storage;

    @Override
    public void init() {
        storage = new CollectionStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String forward;
        String action = request.getParameter("action");
        int id;
        switch (action == null ? "" : action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                log.info("Delete " + id);
                storage.delete(id);
                response.sendRedirect("meals");
                log.info("Redirect from delete page to mealsList");
                return;
            case "edit":
                forward = INSERT_OR_EDIT;
                id = Integer.parseInt(request.getParameter("id"));
                log.info("Edit " + id);
                request.setAttribute("meal", storage.read(id));
                request.setAttribute("head", "Edit meal");
                break;
            case "insert":
                forward = INSERT_OR_EDIT;
                log.info("Insert new meal");
                request.setAttribute("head", "Add meal");
                break;
            default:
                forward = LIST_MEALS;
                log.info("Forward to listMeals page");
                request.setAttribute("list", MealsUtil.filteredByStreamsList(storage.getList(), caloriesPerDay, (m) -> true));
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);

        Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String mealid = request.getParameter("id");
        if (mealid == null || mealid.isEmpty()) {
            storage.create(meal);
            log.info("Created new meal " + meal);
        } else {
            meal.setid(Integer.parseInt(mealid));
            storage.update(meal);
            log.info("Edited meal " + meal);
        }
        response.sendRedirect("meals");
        log.info("Redirect from delete page to mealsList");
    }
}
