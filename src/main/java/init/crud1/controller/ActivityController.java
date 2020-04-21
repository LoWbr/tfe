package init.crud1.controller;

import init.crud1.entity.*;
import init.crud1.form.ActivityForm;
import init.crud1.repository.*;
import init.crud1.service.ActivityService;
import init.crud1.service.SportsManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@Controller
public class ActivityController {

    private ActivityService activityService;
    private SportsManService sportsManService;

    @Autowired
    public ActivityController(ActivityService activityService, SportsManService sportsManService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
    }

    @RequestMapping(value ="/events", method = RequestMethod.GET)
    public String getAllEvents(Model model) {
        model.addAttribute("allActivities", activityService.getAllActivities());
        return "events";
    }

    @RequestMapping(value ="/create", method = RequestMethod.GET)
    public String createEvent(Model model) {
        ActivityForm activityForm = new ActivityForm();
        model.addAttribute("activityForm", activityForm);//new ActivityForm() à tester!!
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        model.addAttribute("allLevels", activityService.getAllLevels());
        return "createEvent";
    }

    @RequestMapping(value = "saveEvent", method = RequestMethod.POST)
    public String saveEvent(@ModelAttribute("ActivityForm") ActivityForm activityForm,
                            Principal principal) throws ParseException {
        activityService.createActivity(activityForm, sportsManService.findCurrentUser(principal.getName()),
                activityService.createAddress(activityForm));
        return "redirect:/events";
    }

    @RequestMapping(value = "/event{id}", method = RequestMethod.GET)
    public String eventDetails(@RequestParam Long id, Model model, Principal principal) {
        if(principal != null){
            model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
        }
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);//Raccourci encore faisable
        model.addAttribute("participants", activity.getRegistered());
        model.addAttribute("comments", activityService.findCommentsForActivity(activity));
        return "eventDetails";
    }

    @RequestMapping(value = "/eventsByCreator", method = RequestMethod.GET)
    public String getActivitiesByCreator(Model model, Principal principal) {
        model.addAttribute("ownCreations",
                activityService.getAllOfTheSameCreator(sportsManService.findCurrentUser(principal.getName())));
        return "ownEvents";
    }

    @RequestMapping(value = "/ownEvent{id}", method = RequestMethod.GET)
    public String ownEventDetails(@RequestParam Long id, Model model) {
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);//Raccourci encore faisable
        model.addAttribute("candidates", activity.getCandidate());
        model.addAttribute("participants", activity.getRegistered());
        model.addAttribute("comments", activityService.findCommentsForActivity(activity));
        return "ownEventDetails";
    }

    @RequestMapping(value = "/event/update{id}", method = RequestMethod.GET)
    public String updateEventForm(@RequestParam Long id, Model model) {
        ActivityForm activityForm = new ActivityForm(activityService.getSpecificActivity(id),
                activityService.getSpecificActivity(id).getAddress());
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allLevels", activityService.getAllLevels());
        return "updateEvent";
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("activityForm") ActivityForm activityForm) {
        //1.Récup Address via l'id de l'activity dans le form
        //2.Update
        //3.Update Activity
        activityService.updateActivity(activityService.getSpecificActivity(activityForm.getId()), activityForm);
        return "events";
    }

    @RequestMapping(value = "/postulate{id}", method = RequestMethod.GET)
    public String applyAsCandidate(@RequestParam(value = "id") Long id, Principal principal) {
        activityService.applyAsCandidate(activityService.getSpecificActivity(id),
                sportsManService.findCurrentUser(principal.getName()));
        return "redirect:/events";
    }

    @RequestMapping(value = "/refuseUser{id}", method = RequestMethod.POST)
    public String refuseUser(@RequestParam(value = "candidate") Long idParticipant,
                          @RequestParam(value = "id") Long idActivity) {
        activityService.refuseBuyer(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idParticipant));
        return "redirect:/events";
    }

    @RequestMapping(value = "/addUser{id}", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "candidate") Long idParticipant,
                          @RequestParam(value = "id") Long idActivity) {
        activityService.addOrRemoveParticipants(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idParticipant),true);
        return "redirect:/events";
    }

    @RequestMapping(value = "/removeUser{id}", method = RequestMethod.POST)
    public String removeUser(@RequestParam(value = "participant") Long idParticipant,
                             @RequestParam(value = "id") Long idActivity) {
        activityService.addOrRemoveParticipants(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idParticipant),false);
        return "redirect:/events";
    }

    @RequestMapping(value = "/close{id}", method = RequestMethod.GET)
    public String closeEvent(@RequestParam(value = "id") Long id) {
        activityService.closeActivity(activityService.getSpecificActivity(id));
        return "redirect:/events";
    }
}
