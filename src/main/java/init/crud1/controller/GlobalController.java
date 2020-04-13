package init.crud1.controller;

import init.crud1.entity.Level;
import init.crud1.form.SportsManForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalController {

    @RequestMapping("/")
    public String getHome(Model model) {
        int test1 = 5;
        int test2 = 10;
        model.addAttribute("test1",test1);
        model.addAttribute("test2",test2);
        return "home";
    }

    @RequestMapping("/signIn")
    public String signIn() {
        return "signIn";
    }

    @RequestMapping("/signUp")
    public String newUser(Model model) {
        SportsManForm sportsManForm = new SportsManForm();
        model.addAttribute("sportsManForm", sportsManForm);
        return "signUp";
    }

    @RequestMapping("/contactUs")
    public String contactUs() {
        return "contactUs";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/search")
    public String search() {
        return "search";
    }

}
