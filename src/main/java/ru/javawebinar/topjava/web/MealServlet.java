package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepositoryList;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/editMeal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private final Repository mealRepository;

    public MealServlet() {
        super();
        mealRepository = new MealRepositoryList();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealRepository.delete(id);
            response.sendRedirect(request.getContextPath() + "/meals");
            return;
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealRepository.get(id);
            request.setAttribute("meal", meal);
        } else {
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.createTos(mealRepository.getAll()));
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));

        String id = req.getParameter("mealid");
        if (id == null || id.isEmpty()) {
            meal.setDateTime(LocalDateTime.parse(req.getParameter("dob")));
            mealRepository.add(meal);
        } else {
            meal.setDateTime(TimeUtil.formatToLocalDateTime(req.getParameter("dob"), "MM/dd/yyyy HH:mm"));
            int uid = Integer.parseInt(id);
            meal.setId(uid);
            mealRepository.update(uid, meal);
        }
        resp.sendRedirect(req.getContextPath() + "/meals");
    }
}
