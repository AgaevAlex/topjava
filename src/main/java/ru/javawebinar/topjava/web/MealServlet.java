package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.caloriesPerDay;

public class MealServlet extends HttpServlet {
    private static final List<MealTo> meals = MealsUtil.createMeals();
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("list", meals);
        request.setAttribute("caloriesPerDay", caloriesPerDay);
        log.debug("redirect to meals table");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
