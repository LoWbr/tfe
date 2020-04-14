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
/*
@RequestMapping("/event")
*/
public class ActivityController {

    private ActivityService activityService;
    private SportsManService sportsManService;

    @Autowired
    public ActivityController(ActivityService activityService, SportsManService sportsManService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
    }

    @RequestMapping("/events")
    public String getAllEvents(Model model) {
        model.addAttribute("allActivities", activityService.getAllActivities());
        return "events";
    }

    @RequestMapping("/create")
    public String getHome(Model model) {
        ActivityForm activityForm = new ActivityForm();
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        model.addAttribute("allLevels", activityService.getAllLevels());
        return "createEvent";
    }

    @RequestMapping(value = "saveEvent", method = RequestMethod.POST)
    public String saveEvent(@ModelAttribute("ActivityForm") ActivityForm activityForm,
                            Principal principal) throws ParseException {
        SportsMan sportsMan = sportsManService.findCurrentUser(principal.getName());
        Activity activity = new Activity(activityForm, sportsMan);
        activityService.saveActivity(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/event{id}", method = RequestMethod.GET)
    public String eventDetails(@RequestParam Long id, Model model, Principal principal) {
        if(principal != null){
            model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
        }
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);
        Iterable<SportsMan> participants = activity.getRegistered();
        model.addAttribute("participants", participants);
        Iterable<Comment> comments = activityService.findCommentsForActivity(activity);
        model.addAttribute("comments", comments);
        return "eventDetails";
    }

    @RequestMapping(value = "/ownEvent{id}", method = RequestMethod.GET)
    public String ownEventDetails(@RequestParam Long id, Model model) {
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);
        model.addAttribute("candidates", activity.getCandidate());
        model.addAttribute("participants", activity.getRegistered());
        Iterable<Comment> comments = activityService.findCommentsForActivity(activity);
        model.addAttribute("comments", comments);
        return "ownEventDetails";
    }

    @RequestMapping(value = "/event/update{id}", method = RequestMethod.GET)
    public String getUpdateEventForm(@RequestParam Long id, Model model) {
        ActivityForm activityForm = new ActivityForm(activityService.getSpecificActivity(id));
        model.addAttribute("allKinds", activityService.getAllActivityTypes());
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allLevels", activityService.getAllLevels());
        return "updateEvent";
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("activityForm") ActivityForm activityForm) {
        Activity activity = activityService.getSpecificActivity(activityForm.getId());
        activity.update(activityForm);
        activityService.saveActivity(activity);
        return "events";
    }

    @RequestMapping(value = "/postulate{id}", method = RequestMethod.GET)
    public String applyAsCandidate(@RequestParam(value = "id") Long id, Principal principal) {
        Activity activity = activityService.getSpecificActivity(id);
        activity.getCandidate().add(sportsManService.findCurrentUser(principal.getName()));
        activityService.saveActivity(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/addUser{id}", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "candidate") Long[] idParticipant,
                          @RequestParam(value = "id") Long id) {
        Activity activity = activityService.getSpecificActivity(id);
        for (Long user : idParticipant) {
            activity.addParticipant(sportsManService.findSpecificUser(user));
            activity.getCandidate().remove(sportsManService.findSpecificUser(user));
        }
        activityService.saveActivity(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/removeUser{id}", method = RequestMethod.POST)
    public String removeUser(@RequestParam(value = "participant") Long[] idParticipant,
                             @RequestParam(value = "id") Long id) {
        Activity activity = activityService.getSpecificActivity(id);
        for (Long user : idParticipant) {
            activity.removeParticipant(sportsManService.findSpecificUser(user));
        }
        activityService.saveActivity(activity);
        return "redirect:/events";
    }

    //!!A pusher dans le service!!
    @RequestMapping(value = "/close{id}", method = RequestMethod.GET)
    public String close(@RequestParam(value = "id") Long id) {
        Activity activity = activityService.getSpecificActivity(id);
        activity.closeEvent();
        for (SportsMan sportsman : activity.getRegistered()) {
            double durationInHours = (double) activity.getDuration() / 60;
            Integer energeticExpenditure = Math.toIntExact(Math.round(sportsman.getWeight() * durationInHours * activity.getActivity().getMet()));
            sportsman.setPoints(energeticExpenditure);
            if (sportsman.checkLevelStatus()){
                Long new_place = sportsman.getLevel().getPlace()+1;
                sportsman.setLevel(sportsManService.findSpecificLevel(new_place));
            }
            sportsManService.saveUser(sportsman);
            Statistic statistic = new Statistic(sportsman, activity, energeticExpenditure);
            sportsManService.saveStatistic(statistic);
        }
        activityService.saveActivity(activity);
        return "redirect:/events";
    }
}
