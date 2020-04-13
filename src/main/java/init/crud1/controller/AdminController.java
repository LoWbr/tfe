package init.crud1.controller;

import init.crud1.entity.Activity;
import init.crud1.entity.SportsMan;
import init.crud1.form.TopicForm;
import init.crud1.repository.ActivityRepository;
import init.crud1.repository.ActivityTypeRepository;
import init.crud1.repository.SportsManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    SportsManRepository sportsManRepository;

    @RequestMapping("/manage")
    public String getHome(Model model) {
        Iterable<SportsMan> allUser = this.sportsManRepository.findAll();
        Iterable<Activity> allActivities = this.activityRepository.findAll();
        TopicForm topicForm = new TopicForm();
        model.addAttribute("allUsers", allUser);
        model.addAttribute("allActivities", allActivities);
        model.addAttribute("topicForm", topicForm);
        return "adminPage";
    }

    @RequestMapping(value = "/cancel{id}", method = RequestMethod.GET)
    public String cancel(@RequestParam(value = "id") Long id) {
        Activity activity = activityRepository.findSpecific(id);
            activity.setOpen(false);
            activityRepository.save(activity);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/open{id}", method = RequestMethod.GET)
    public String open(@RequestParam(value = "id") Long id) {
        Activity activity = activityRepository.findSpecific(id);
            activity.setOpen(true);
            activityRepository.save(activity);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/user/block{id}", method = RequestMethod.GET)
    public String block(@RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManRepository.findSpecific(id);
        sportsMan.setBlocked(true);
        for (Activity activity : sportsMan.getCreatedActivities()) {
            activity.setOpen(false);
            activityRepository.save(activity);
        }
        sportsManRepository.save(sportsMan);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/user/unblock{id}", method = RequestMethod.GET)
    public String unblock(@RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManRepository.findSpecific(id);
        sportsMan.setBlocked(false);
        for (Activity activity : sportsMan.getCreatedActivities()) {
            activity.setOpen(true);
            activityRepository.save(activity);
        }
        sportsManRepository.save(sportsMan);
        return "redirect:/manage";
    }

}
