package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    protected JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String getMeals(Model model, HttpServletRequest request) {
        log.info("users");
        model.addAttribute("meals", getAll());
        return "meals";
    }

}