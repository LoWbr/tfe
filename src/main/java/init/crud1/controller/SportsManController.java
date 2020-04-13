package init.crud1.controller;

import init.crud1.entity.*;
import init.crud1.exception.UserAlreadyExistException;
import init.crud1.form.SportsManForm;
import init.crud1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
/*
@RequestMapping("/user")
*/
public class SportsManController {

    @Autowired
    SportsManRepository sportsManRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/users")
    public String getHome(Model model) {
        Iterable<SportsMan> allUsers = sportsManRepository.findAll();
        model.addAttribute("allUsers", allUsers);
        return "users";
    }

    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    public String saveEvent(@Valid /*@ModelAttribute("EventForm")*/ SportsManForm sportsManForm, BindingResult bindingResult) throws UserAlreadyExistException {

        if(bindingResult.hasErrors()){
            System.out.println("Errors: " + bindingResult.getErrorCount());
            return "signUp";
        }

        SportsMan sportsMan = new SportsMan(sportsManForm);
        Level level = levelRepository.findSpecific(new Long(1));
        sportsMan.setLevel(level);
        List<Role> roleList = roleRepository.findForInitialize(new Long(1));
        sportsMan.setRoles(roleList);
        sportsMan.setPassword(passwordEncoder.encode(sportsManForm.getPassword()));
        if(this.sportsManRepository.findSpecific(sportsMan.getEmail()) != null) {
            bindingResult.rejectValue("mail", "", "This account already exists");
            return "signUp";
        }
        else if(!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())){
            bindingResult.rejectValue("password","","The two passwords do not match.");
            return "signUp";
        }
        else{
            sportsManRepository.save(sportsMan);
        }
        return "users";

        /* VALIDE

        if (this.sportsManRepository.findSpecific(sportsMan.getEmail()) == null) {
                sportsManRepository.save(sportsMan);
            } else {
                bindingResult.rejectValue("mail", "", "This account already exists");
                return "signUp";
            }*/
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.GET)
    public String getUpdateSportsManForm(Model model, Principal principal) {
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        SportsManForm sportsManForm = new SportsManForm(sportsMan);
        model.addAttribute("sportsManForm", sportsManForm);
        return "updateUser";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("sportsManForm") SportsManForm sportsManForm, Principal principal) {
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        sportsMan.updateSportsMan(sportsManForm);
        this.sportsManRepository.save(sportsMan);
        return "users";
    }

    @RequestMapping(value = "/ownEvents", method = RequestMethod.GET)
    public String block(Model model, Principal principal) {
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        List<Activity> ownCreations = this.activityRepository.findByCreator(sportsMan);
        model.addAttribute("ownCreations",ownCreations);
        return "ownEvents";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userDetails(Principal principal, Model model) {
        SportsMan sportsMan = this.sportsManRepository.findSpecific(principal.getName());
        if (sportsMan.getContacts().size() == 0) {
            Iterable<SportsMan> allUsers = sportsManRepository.findAllWithoutMe(sportsMan.getId());
            model.addAttribute("allUsers", allUsers);
        } else {
            Iterable<SportsMan> allUsers = sportsManRepository.findNotContacts(sportsMan.getContacts(), sportsMan.getId());
            model.addAttribute("allUsers", allUsers);
        }
        Iterable<SportsMan> contacts = sportsMan.getContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("sportsMan", sportsMan);
        Iterable<Statistic> statistics = statisticRepository.findBySportsMan(sportsMan);
        model.addAttribute("statistics", statistics);
        return "userDetails";
    }

    @RequestMapping(value = "/getMakeEvent{id}", method = RequestMethod.GET)
    public String getMakeEvent(@RequestParam Long id, Model model) {
        SportsMan sportsMan = this.sportsManRepository.findSpecific(id);
        model.addAttribute("sportsMan", sportsMan);
        return "userParticipatedEvents";
    }

    @RequestMapping(value = "/addContact{id}", method = RequestMethod.POST)
    public String addContact(@RequestParam(value = "contact") Long[] idContact,
                             @RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManRepository.findSpecific(id);
        for (Long user : idContact) {
            sportsMan.addContact(sportsManRepository.findSpecific(user));
        }
        sportsManRepository.save(sportsMan);
        return "redirect:/users";
    }

    @RequestMapping(value = "/removeContact{id}", method = RequestMethod.POST)
    public String removeContact(@RequestParam(value = "contact") Long[] idContact,
                                @RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManRepository.findSpecific(id);
        for (Long user : idContact) {
            sportsMan.removeContact(sportsManRepository.findSpecific(user));
        }
        sportsManRepository.save(sportsMan);
        return "redirect:/users";
    }

}
