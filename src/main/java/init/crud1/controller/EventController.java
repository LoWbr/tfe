package init.crud1.controller;

import init.crud1.entity.*;
import init.crud1.form.ActivityForm;
import init.crud1.repository.*;
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
public class EventController {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    SportsManRepository sportsManRepository;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LevelRepository levelRepository;

    @RequestMapping("/events")
    public String getAllEvents(Model model) {
        Iterable<Activity> allActivities = activityRepository.findAll();
        model.addAttribute("allActivities", allActivities);
        return "events";
    }

    @RequestMapping("/create")
    public String getHome(Model model) {
        ActivityForm activityForm = new ActivityForm();
        Iterable<SportsMan> allUsers = sportsManRepository.findAll();
        Iterable<ActivityType> allKinds = activityTypeRepository.findAll();
        Iterable<Level> allLevels = levelRepository.findAll();
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allKinds", allKinds);
        model.addAttribute("allLevels", allLevels);
        return "createEvent";
    }

    @RequestMapping(value = "saveEvent", method = RequestMethod.POST)
    public String saveEvent(@ModelAttribute("ActivityForm") ActivityForm activityForm, Principal principal) throws ParseException {
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        Activity activity = new Activity(activityForm, sportsMan);
        activityRepository.save(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/event{id}", method = RequestMethod.GET)
    public String eventDetails(@RequestParam Long id, Model model, Principal principal) {
        if(principal != null){
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
            model.addAttribute("sportsMan", sportsMan);
        }
        Activity activity = activityRepository.findSpecific(id);
        model.addAttribute("activity", activity);
        Iterable<SportsMan> participants = activity.getRegistered();
        model.addAttribute("participants", participants);
        Iterable<Comment> comments = commentRepository.findForEvent(activity);
        model.addAttribute("comments", comments);
        return "eventDetails";
    }

    @RequestMapping(value = "/ownEvent{id}", method = RequestMethod.GET)
    public String ownEventDetails(@RequestParam Long id, Model model) {
        Activity activity = activityRepository.findSpecific(id);
        model.addAttribute("activity", activity);
        Iterable<SportsMan> candidates = activity.getCandidate();
        model.addAttribute("candidates", candidates);
        Iterable<SportsMan> participants = activity.getRegistered();
        model.addAttribute("participants", participants);
        Iterable<Comment> comments = commentRepository.findForEvent(activity);
        model.addAttribute("comments", comments);
        return "ownEventDetails";
    }

    @RequestMapping(value = "/event/update{id}", method = RequestMethod.GET)
    public String getUpdateEventForm(@RequestParam Long id, Model model) {
        ActivityForm activityForm = new ActivityForm(this.activityRepository.findSpecific(id));
        Iterable<ActivityType> allKinds = activityTypeRepository.findAll();
        Iterable<Level> allLevels = levelRepository.findAll();
        model.addAttribute("allKinds", allKinds);
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allLevels", allLevels);
        return "updateEvent";
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("activityForm") ActivityForm activityForm) {
        Activity activity = this.activityRepository.findSpecific(activityForm.getId());
        activity.update(activityForm);
        this.activityRepository.save(activity);
        return "events";
    }

    @RequestMapping(value = "/postulate{id}", method = RequestMethod.GET)
    public String applyAsCandidate(@RequestParam(value = "id") Long id, Principal principal) {
        Activity activity = activityRepository.findSpecific(id);
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        activity.getCandidate().add(sportsMan);
        activityRepository.save(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/addUser{id}", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "candidate") Long[] idParticipant,
                          @RequestParam(value = "id") Long id) {
        Activity activity = activityRepository.findSpecific(id);
        for (Long user : idParticipant) {
            activity.addParticipant(sportsManRepository.findSpecific(user));
            activity.getCandidate().remove(sportsManRepository.findSpecific(user));
        }
        activityRepository.save(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/removeUser{id}", method = RequestMethod.POST)
    public String removeUser(@RequestParam(value = "participant") Long[] idParticipant,
                             @RequestParam(value = "id") Long id) {
        Activity activity = activityRepository.findSpecific(id);
        for (Long user : idParticipant) {
            activity.removeParticipant(sportsManRepository.findSpecific(user));
        }
        activityRepository.save(activity);
        return "redirect:/events";
    }

    @RequestMapping(value = "/close{id}", method = RequestMethod.GET)
    public String close(@RequestParam(value = "id") Long id) {
        Activity activity = activityRepository.findSpecific(id);
        activity.closeEvent();
        for (SportsMan sportsman : activity.getRegistered()) {
            double durationInHours = (double) activity.getDuration() / 60;
            Integer energeticExpenditure = Math.toIntExact(Math.round(sportsman.getWeight() * durationInHours * activity.getActivity().getMet()));
            sportsman.setPoints(energeticExpenditure);
            if (sportsman.checkLevelStatus()){
                Long new_place = sportsman.getLevel().getPlace()+1;
                sportsman.setLevel(levelRepository.findSpecific(new_place));
            }
            sportsManRepository.save(sportsman);
            Statistic statistic = new Statistic(sportsman, activity, energeticExpenditure);
            statisticRepository.save(statistic);
        }
        activityRepository.save(activity);
        return "redirect:/events";
    }
}
