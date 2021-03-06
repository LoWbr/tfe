package init.crud1.controller;

import init.crud1.form.SearchActivityForm;
import init.crud1.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@Controller
public class GlobalController {

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "global/home";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signIn(@RequestParam(value = "error", required = false) String error,
                         Model model) {
        String warning = null;
        if(error != null){
            byte[] getErrorBytes = Base64.getDecoder().decode(error);
            warning = new String(getErrorBytes);
        }
        model.addAttribute("warning",warning);
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

    @RequestMapping(value ="/search", method = RequestMethod.GET)
    public String search(@ModelAttribute("searchActivityForm") SearchActivityForm searchActivityForm,
                         Model model) {
        model.addAttribute("allLevels", activityService.getAllLevels());
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        model.addAttribute("allEvents",activityService.getAllActivities());
        model.addAttribute("searchActivityForm",searchActivityForm );
        return "search";
    }

    @RequestMapping(value ="/searchByFilter", method = RequestMethod.POST)
    public String searchByFilter(@ModelAttribute("searchActivityForm") SearchActivityForm searchActivityForm,
                         Model model) {
        model.addAttribute("allLevels", activityService.getAllLevels());
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        model.addAttribute("allEvents", activityService.findForSearch(searchActivityForm));
        model.addAttribute("searchActivityForm", searchActivityForm);
        return "search";

    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("msg", "Hi user, you do not have permission to access this page!");
        } else {
            model.addAttribute("msg",
                    "It seems that your are not connected.");
        }

        return "accessdenied";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "test";
    }

}
