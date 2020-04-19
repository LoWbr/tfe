package init.crud1.controller;

import init.crud1.entity.Activity;
import init.crud1.entity.Level;
import init.crud1.form.SearchActivityForm;
import init.crud1.form.SportsManForm;
import init.crud1.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GlobalController {

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "home";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signIn() {
        return "signIn";
    }

    @RequestMapping(value = "/contactUs", method = RequestMethod.GET)
    public String contactUs() {
        return "contactUs";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    @RequestMapping("/search")
    public String search(@ModelAttribute("searchActivityForm") SearchActivityForm searchActivityForm,
                         Model model, @RequestParam(required = false) Boolean there) {
        model.addAttribute("allLevels", activityService.getAllLevels());
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        //if requestparam flag
        if(there != null){
            model.addAttribute("allEvents",activityService.findForSearch(searchActivityForm));
            model.addAttribute("searchActivityForm",searchActivityForm );
            return "search";
        }
        //else
        else{
            model.addAttribute("allEvents",activityService.getAllActivities());
            model.addAttribute("searchActivityForm",searchActivityForm );
            return "search";
        }
    }

}
