package init.crud1.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import init.crud1.exception.UserAlreadyExistException;
import init.crud1.form.SportsManForm;
import init.crud1.service.ActivityService;
import init.crud1.service.ManagementService;
import init.crud1.service.SportsManService;

@Controller
public class SportsManController {

	@Autowired
	SportsManService sportsManService;

	@Autowired
	ActivityService activityService;

	@Autowired
	ManagementService managementService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value= "/users", method = RequestMethod.GET)
	public String getSportsMen(Model model) {
		model.addAttribute("allUsers", sportsManService.getAllUser());
		return "users";
	}

	@RequestMapping(value="/signUp", method = RequestMethod.GET)
	public String createSportsMan(Model model) {
		model.addAttribute("sportsManForm", new SportsManForm());
		return "signUp";
	}

	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveSportsMan(@Valid SportsManForm sportsManForm, BindingResult bindingResult) throws UserAlreadyExistException {

		if(bindingResult.hasErrors()){
			System.out.println("Errors: " + bindingResult.getErrorCount());
			return "signUp";
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateInput = LocalDate.parse(sportsManForm.getDateofBirth(),formatter).plusDays(1);
		LocalDate current = LocalDate.now();

		if(this.sportsManService.findCurrentUser(sportsManForm.getMail()) != null) {
			bindingResult.rejectValue("mail", "", "This account already exists");
			return "signUp";
		}
		else if(Period.between(dateInput,current).getYears() < 18){
			System.out.println(Period.between(dateInput,current).getYears());
			bindingResult.rejectValue("dateofBirth","","You must have 18 years old to register");
			return "signUp";
		}
		else if(!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())){
			bindingResult.rejectValue("password","","The two passwords do not match.");
			return "signUp";
		}
		else{
			sportsManService.createUser(sportsManForm); //NB : if update de l'email, déconnexion à mettre en place
		}                                               // ou refresh!!! (à creuser!!)
		return "users";
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.GET)
	public String updateSportsManForm(Model model, Principal principal) {
		model.addAttribute("sportsManForm",
				new SportsManForm(sportsManService.findCurrentUser(principal.getName())));
		return "updateUser";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateSportsMan(@Valid SportsManForm sportsManForm,
			BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()){
			return "user";
		}
		sportsManService.updateUser(sportsManService.findCurrentUser(principal.getName()), sportsManForm);
		return "users";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String sportsManOwnDetails(Principal principal, Model model) {
		model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
		model.addAttribute("allUsers",
				sportsManService.getPotentialContacts(sportsManService.findCurrentUser(principal.getName())));
		model.addAttribute("statistics",
				sportsManService.findBySportsMan(sportsManService.findCurrentUser(principal.getName())));
		return "userDetails";
	}

	@RequestMapping(value = "/getRegisteredEvents{id}", method = RequestMethod.GET)
	public String getRegisteredEvents(@RequestParam Long id, Model model) {
		model.addAttribute("sportsMan", sportsManService.findSpecificUser(id));
		return "userParticipatedEvents";
	}

	@RequestMapping(value = "/addContact{id}", method = RequestMethod.POST)
	public String addContact(@RequestParam(value = "contact") Long idContact,
			Principal principal) {
		sportsManService.addOrRemoveContacts(sportsManService.findCurrentUser(principal.getName()),
				sportsManService.findSpecificUser(idContact), true);
		return "redirect:/user";
	}

	@RequestMapping(value = "/removeContact{id}", method = RequestMethod.POST)
	public String removeContact(@RequestParam(value = "contact") Long idContact,
			Principal principal) {
		sportsManService.addOrRemoveContacts(sportsManService.findCurrentUser(principal.getName()),
				sportsManService.findSpecificUser(idContact), false);
		return "redirect:/user";
	}

	@RequestMapping(value = "/applyAsConfirmedUser", method = RequestMethod.GET)
	public String applyAsConfirmedUser(Principal principal) {
		sportsManService.applyForConfirmedRole(sportsManService.findCurrentUser(principal.getName()));
		return "redirect:/user";
	}

}
