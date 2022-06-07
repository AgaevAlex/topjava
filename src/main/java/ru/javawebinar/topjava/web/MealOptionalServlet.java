package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.optional.ListStorage;
import ru.javawebinar.topjava.optional.Storage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealOptionalServlet extends HttpServlet {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static String INSERT_OR_EDIT = "/addEditMeals.jsp";
    private static String LIST_MEALS = "/listMeals.jsp";
    private final Storage storage;

    public MealOptionalServlet() {
        super();
        storage = new ListStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int uuid = Integer.parseInt(request.getParameter("uuid"));
            storage.delete(uuid);
            response.sendRedirect("/topjava");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int uuid = Integer.parseInt(request.getParameter("uuid"));
            Meal meal = storage.read(uuid);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeals")) {
            forward = LIST_MEALS;
            request.setAttribute("meals", storage.getListMeals());
        } else {
            forward = INSERT_OR_EDIT;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);

        Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String userid = request.getParameter("userid");
        if (userid == null || userid.isEmpty()) {
            storage.create(meal);
        } else {
            meal.setUuid(Integer.parseInt(userid));
            storage.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("list", storage.getListMeals());
        view.forward(request, response);
    }
}
