package init.crud1.controller;

import init.crud1.entity.*;
import init.crud1.form.*;
import init.crud1.service.ActivityService;
import init.crud1.service.ManagementService;
import init.crud1.service.NewsService;
import init.crud1.service.SportsManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ManagementController {

    private ActivityService activityService;
    private SportsManService sportsManService;
    private ManagementService managementService;
    private NewsService newsService;

    @Autowired
    public ManagementController(ActivityService activityService, SportsManService sportsManService,
                                ManagementService managementService, NewsService newsService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
        this.managementService = managementService;
        this.newsService = newsService;
    }

    @RequestMapping("/manageUsers")
    public String manageUserSetting(Model model) {
        TopicForm topicForm = new TopicForm();
        model.addAttribute("allUsers", sportsManService.getAllUser());
        model.addAttribute("allCandidates", managementService.getPromotionCandidates());
        model.addAttribute("topicForm", topicForm);
        return "manageUsers";
    }

    @RequestMapping("/manageEvents")
    public String manageEventSetting(Model model) {
        model.addAttribute("allActivities", activityService.getAllActivities());
        return "manageEvents";
    }

    @RequestMapping(value = "/cancel{id}", method = RequestMethod.GET)
    public String cancel(@RequestParam(value = "id") Long id) {
        activityService.cancelOrActivateActivity(activityService.getSpecificActivity(id), false);
        newsService.returnCancelledApplictionNewOrCloseEventNew(activityService.getSpecificActivity(id),
                NewsType.CANCELLED_EVENT);
        return "redirect:/manageEvents";
    }

    @RequestMapping(value = "/open{id}", method = RequestMethod.GET)
    public String open(@RequestParam(value = "id") Long id) {
        activityService.cancelOrActivateActivity(activityService.getSpecificActivity(id), true);
        return "redirect:/manageEvents";
    }

    @RequestMapping(value = "/user/block{id}", method = RequestMethod.GET)
    public String block(@RequestParam(value = "id") Long id) {
        sportsManService.blockOrUnblock(sportsManService.findSpecificUser(id),true);
        for (Activity activity : sportsManService.findSpecificUser(id).getCreatedActivities()) {
            activityService.cancelOrActivateActivity(activity,false);
        }
        return "redirect:/manageUsers";
    }

    @RequestMapping(value = "/user/unblock{id}", method = RequestMethod.GET)
    public String unblock(@RequestParam(value = "id") Long id) {
        sportsManService.blockOrUnblock(sportsManService.findSpecificUser(id),false);
        for (Activity activity : sportsManService.findSpecificUser(id).getCreatedActivities()) {
            activityService.cancelOrActivateActivity(activity,true);
        }
        return "redirect:/manageUsers";
    }

    @RequestMapping(value = "/refusePromotion{id}", method = RequestMethod.GET)
    public String refusePromotionUser(@RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManService.findSpecificUser(id);
        managementService.removeRequest(managementService.findSpecific(sportsMan));
        newsService.makeNews(NewsType.NEGATIVE_REQUEST, sportsMan, null);
        return "redirect:/manageUsers";
    }

    @RequestMapping(value = "/promote{id}", method = RequestMethod.GET)
    public String promoteUser(@RequestParam(value = "id") Long id) {
        sportsManService.promoteUser(sportsManService.findSpecificUser(id));
        managementService.removeRequest(managementService.findSpecific
                (sportsManService.findSpecificUser(id)));
        newsService.makeNews(NewsType.VALIDATED_REQUEST,
                sportsManService.findSpecificUser(id), null);
        //Add notification!!
        return "redirect:/manageUsers";
    }

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
    public String createTopic(@ModelAttribute("topicForm") TopicForm topicForm,
                              Principal principal) {
        this.managementService.addTopic(sportsManService.findCurrentUser(principal.getName()),
                topicForm);
        return "redirect:/manage";
    }

    @RequestMapping(value = "/manageSportsSetting", method = RequestMethod.GET)
    public String manageSportsSetting(Model model) {
        model.addAttribute("activityTypeForm",new ActivityTypeForm());
        model.addAttribute("activityTypes",activityService.getAllActivityTypes());
        return "setSportsSetting";
    }

    @RequestMapping(value = "/updateType{id}", method = RequestMethod.POST)
    public String updateType(@RequestParam(value = "id") Long id, @ModelAttribute("activityTypeForm") ActivityTypeForm activityTypeForm) {
        managementService.updateType(managementService.findSpecificActivityType(id),activityTypeForm);
        return "redirect:/manageSportsSetting";
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public String addType(@ModelAttribute("activityTypeForm") ActivityTypeForm activityTypeForm) {
        managementService.createType(activityTypeForm);
        return "redirect:/manageSportsSetting";
    }

    @RequestMapping(value = "/manageLevelsSetting", method = RequestMethod.GET)
    public String manageLevelsSetting(Model model) {
        model.addAttribute("levelForm",new LevelForm());
        model.addAttribute("activityLevels",managementService.getAllLevels());
        return "setLevelsSetting";
    }

    @RequestMapping(value = "/updateLevel{id}", method = RequestMethod.POST)
    public String updateLevel(@RequestParam(value = "id") Long id, @ModelAttribute("levelForm") LevelForm levelForm) {
        managementService.updateLevel(levelForm,managementService.findSpecificLevel(id));
        return "redirect:/manageLevelsSetting";
    }

    @RequestMapping(value = "/history")
    public String getHistory(@ModelAttribute("searchNewForm") SearchNewForm searchNewForm,
                             Model model, @RequestParam(required = false) Boolean there) {
        model.addAttribute("allTypes", newsService.getAllNewsType());
        if(there != null){ ;
            model.addAttribute("allActs",newsService.findForSearch(searchNewForm));
            model.addAttribute("searchActivityForm",searchNewForm);
            return "searchNew";
        }
        else{
            model.addAttribute("allActs",newsService.findAll());
            model.addAttribute("searchActivityForm",searchNewForm);
            return "searchNew";
        }
    }

}