package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealRepository repository = new InMemoryMealRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {
            repository.delete(Integer.parseInt(request.getParameter("mealId")));
            request.setAttribute("meals", repository.getAllMeal());
        } else if (action.equalsIgnoreCase("insert")) {
            request.getRequestDispatcher("/saveMeals.jsp").forward(request, response);
            return;
        } else {
            request.setAttribute("meals", repository.getAllMeal());
        }
        log.debug("redirect to meals");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        // log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");


    }
}
