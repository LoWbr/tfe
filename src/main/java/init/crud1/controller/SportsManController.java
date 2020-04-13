package init.crud1.controller;

import init.crud1.entity.*;
import init.crud1.exception.UserAlreadyExistException;
import init.crud1.form.SportsManForm;
import init.crud1.repository.*;
import init.crud1.service.ActivityService;
import init.crud1.service.SportsManService;
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
public class SportsManController {

    @Autowired
    SportsManService sportsManService;

    @Autowired
    ActivityService activityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/users")
    public String getHome(Model model) {
        model.addAttribute("allUsers", sportsManService.getAllUser());
        return "users";
    }

    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    public String saveEvent(@Valid SportsManForm sportsManForm, BindingResult bindingResult) throws UserAlreadyExistException {

        if(bindingResult.hasErrors()){
            System.out.println("Errors: " + bindingResult.getErrorCount());
            return "signUp";
        }
        SportsMan sportsMan = new SportsMan(sportsManForm);
        sportsMan.setLevel(sportsManService.findSpecificLevel(new Long(1)));
        sportsMan.setRoles(sportsManService.findSpecificRole(new Long(1)));
        sportsMan.setPassword(passwordEncoder.encode(sportsManForm.getPassword()));
        if(this.sportsManService.findCurrentUser(sportsMan.getEmail()) != null) {
            bindingResult.rejectValue("mail", "", "This account already exists");
            return "signUp";
        }
        else if(!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())){
            bindingResult.rejectValue("password","","The two passwords do not match.");
            return "signUp";
        }
        else{
            sportsManService.saveUser(sportsMan);
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
        SportsManForm sportsManForm = new SportsManForm(sportsManService.findCurrentUser(principal.getName()));
        model.addAttribute("sportsManForm", sportsManForm);
        return "updateUser";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("sportsManForm") SportsManForm sportsManForm, Principal principal) {
        SportsMan sportsMan = sportsManService.findCurrentUser(principal.getName());
        sportsMan.updateSportsMan(sportsManForm);
        sportsManService.saveUser(sportsMan);
        return "users";
    }

    @RequestMapping(value = "/ownEvents", method = RequestMethod.GET)
    public String block(Model model, Principal principal) {
        SportsMan sportsMan = this.sportsManService.findCurrentUser(principal.getName());
        model.addAttribute("ownCreations",activityService.getAllOfTheSameCreator(sportsMan));
        return "ownEvents";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userDetails(Principal principal, Model model) {
        SportsMan sportsMan = this.sportsManService.findCurrentUser(principal.getName());
        if (sportsMan.getContacts().size() == 0) {
            model.addAttribute("allUsers", sportsManService.getAllExceptCurrent(sportsMan.getId()));
        } else {
            model.addAttribute("allUsers", sportsManService.getAllNoContats(sportsMan.getContacts(), sportsMan.getId()));
        }
        Iterable<SportsMan> contacts = sportsMan.getContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("sportsMan", sportsMan);
        model.addAttribute("statistics", sportsManService.findBySportsMan(sportsMan));
        return "userDetails";
    }

    @RequestMapping(value = "/getMakeEvent{id}", method = RequestMethod.GET)
    public String getMakeEvent(@RequestParam Long id, Model model) {
        SportsMan sportsMan = this.sportsManService.findSpecificUser(id);
        model.addAttribute("sportsMan", sportsMan);
        return "userParticipatedEvents";
    }

    @RequestMapping(value = "/addContact{id}", method = RequestMethod.POST)
    public String addContact(@RequestParam(value = "contact") Long[] idContact,
                             @RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManService.findSpecificUser(id);
        for (Long user : idContact) {
            sportsMan.addContact(sportsManService.findSpecificUser(user));
        }
        sportsManService.saveUser(sportsMan);
        return "redirect:/users";
    }

    @RequestMapping(value = "/removeContact{id}", method = RequestMethod.POST)
    public String removeContact(@RequestParam(value = "contact") Long[] idContact,
                                @RequestParam(value = "id") Long id) {
        SportsMan sportsMan = sportsManService.findSpecificUser(id);
        for (Long user : idContact) {
            sportsMan.removeContact(sportsManService.findSpecificUser(user));
        }
        sportsManService.saveUser(sportsMan);
        return "redirect:/users";
    }

}
