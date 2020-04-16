package init.crud1.controller;

import init.crud1.entity.Activity;
import init.crud1.entity.ActivityType;
import init.crud1.entity.SportsMan;
import init.crud1.entity.Topic;
import init.crud1.form.ActivityForm;
import init.crud1.form.ActivityTypeForm;
import init.crud1.form.TopicForm;
import init.crud1.repository.ActivityRepository;
import init.crud1.repository.ActivityTypeRepository;
import init.crud1.repository.SportsManRepository;
import init.crud1.service.ActivityService;
import init.crud1.service.ManagementService;
import init.crud1.service.SportsManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private ActivityService activityService;
    private SportsManService sportsManService;
    private ManagementService managementService;

    @Autowired
    public AdminController(ActivityService activityService, SportsManService sportsManService,
                           ManagementService managementService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
        this.managementService = managementService;
    }

    @RequestMapping("/manage")
    public String getHome(Model model) {
        TopicForm topicForm = new TopicForm();
        model.addAttribute("allUsers", sportsManService.getAllUser());
        model.addAttribute("allCandidates", managementService.getPromotionCandidates());
        model.addAttribute("allActivities", activityService.getAllActivities());
        model.addAttribute("topicForm", topicForm);
        return "adminPage";
    }

    @RequestMapping(value = "/cancel{id}", method = RequestMethod.GET)
    public String cancel(@RequestParam(value = "id") Long id) {
        Activity activity = activityService.getSpecificActivity(id);
        activity.setOpen(false);
        activityService.saveActivity(activity);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/open{id}", method = RequestMethod.GET)
    public String open(@RequestParam(value = "id") Long id) {
        Activity activity = activityService.getSpecificActivity(id);
        activity.setOpen(true);
        activityService.saveActivity(activity);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/user/block{id}", method = RequestMethod.GET)
    public String block(@RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManService.findSpecificUser(id);
        sportsMan.setBlocked(true);
        for (Activity activity : sportsMan.getCreatedActivities()) {
            activity.setOpen(false);
            activityService.saveActivity(activity);
        }
        sportsManService.saveUser(sportsMan);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/user/unblock{id}", method = RequestMethod.GET)
    public String unblock(@RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManService.findSpecificUser(id);
        sportsMan.setBlocked(false);
        for (Activity activity : sportsMan.getCreatedActivities()) {
            activity.setOpen(true);
            activityService.saveActivity(activity);
        }
        sportsManService.saveUser(sportsMan);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/promote{id}", method = RequestMethod.GET)
    public String promoteUser(@RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManService.findSpecificUser(id);
        sportsMan.addRoles(sportsManService.findRole((long) 2));
        sportsManService.saveUser(sportsMan);
        managementService.removeRequest(managementService.findSpecific(sportsMan));
        //Add notification!!
        return "redirect:/manage";
    }

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
    public String createTopic(@ModelAttribute("topicForm") TopicForm topicForm,
                              Principal principal) {
        SportsMan sportsMan = sportsManService.findCurrentUser(principal.getName());
        Topic topic = new Topic(sportsMan,topicForm);
        this.managementService.saveTopic(topic);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/manageSportsSetting", method = RequestMethod.GET)
    public String manageSportsSetting(Model model) {
        ActivityTypeForm activityTypeForm = new ActivityTypeForm();
        model.addAttribute("activityTypeForm",activityTypeForm);
        model.addAttribute("activityTypes",activityService.getAllActivityTypes());
        return "setSportsComponent";
    }

    @RequestMapping(value = "/updateType{id}", method = RequestMethod.POST)
    public String updateTopic(@RequestParam(value = "id") Long id, @ModelAttribute("activityTypeForm") ActivityTypeForm activityTypeForm) {
        ActivityType activityType = managementService.findSpecificActivityType(id);
        activityType.update(activityTypeForm);
        this.managementService.saveType(activityType);
        return "redirect:/manageSportsSetting";
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public String createTopic(@ModelAttribute("activityTypeForm") ActivityTypeForm activityTypeForm) {
        ActivityType activityType = new ActivityType();
        activityType.update(activityTypeForm);
        this.managementService.saveType(activityType);
        return "redirect:/manageSportsSetting";
    }

}
