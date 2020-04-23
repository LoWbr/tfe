package init.crud1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import init.crud1.entity.Level;
import init.crud1.entity.NewsType;
import init.crud1.entity.PromotionRequest;
import init.crud1.entity.Role;
import init.crud1.entity.SportsMan;
import init.crud1.entity.Statistic;
import init.crud1.form.SportsManForm;
import init.crud1.repository.LevelRepository;
import init.crud1.repository.RoleRepository;
import init.crud1.repository.SportsManRepository;
import init.crud1.repository.StatisticRepository;

@Service
public class SportsManService {

	SportsManRepository sportsManRepository;
	StatisticRepository statisticRepository;
	LevelRepository levelRepository;
	RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	ManagementService managementService;
	NewsService newsService;

	@Autowired
	public SportsManService(SportsManRepository sportsManRepository, StatisticRepository statisticRepository,
			LevelRepository levelRepository, RoleRepository roleRepository,
			ManagementService managementService, PasswordEncoder passwordEncoder,
			NewsService newsService) {
		this.sportsManRepository = sportsManRepository;
		this.statisticRepository = statisticRepository;
		this.levelRepository = levelRepository;
		this.roleRepository = roleRepository;
		this.managementService = managementService;
		this.passwordEncoder = passwordEncoder;
		this.newsService = newsService;
	}

	//FindCurrentUser
	public SportsMan findCurrentUser(String mail){
		return this.sportsManRepository.findSpecific(mail);
	}

	//FindSpecificUser
	public SportsMan findSpecificUser(Long id){
		return this.sportsManRepository.findSpecific(id);
	}

	//AllUsers
	public Iterable<SportsMan> getAllUser(){
		return this.sportsManRepository.findAll();
	}

	//AllExceptCurrent
	public Iterable<SportsMan> getAllExceptConnectedUser(Long id){
		return this.sportsManRepository.findAllWithoutMe(id);
	}

	//AllNotContactUsers
	public Iterable<SportsMan> getAllNoContats(List<SportsMan> contacts, Long id){
		return this.sportsManRepository.findNotContacts(contacts, id);
	}

	//FindAllNoContactsUser
	public Iterable<SportsMan> getPotentialContacts(SportsMan sportsMan){
		if (sportsMan.getContacts().size() == 0) {
			return this.getAllExceptConnectedUser(sportsMan.getId());
		} else {
			return this.getAllNoContats(sportsMan.getContacts(), sportsMan.getId());
		}
	}

	//ManagingContacts
	public void addOrRemoveContacts(SportsMan sportsMan, SportsMan contact, boolean flag){
		if(flag){
			sportsMan.addContact(contact);
		}
		else{
			sportsMan.removeContact(contact);
		}
		this.saveUser(sportsMan);
	}

	//SaveUser
	public void saveUser(SportsMan sportsMan){
		this.sportsManRepository.save(sportsMan);
	}

	//FindbyUser
	public Iterable<Statistic> findBySportsMan(SportsMan sportsMan){
		return this.statisticRepository.findBySportsMan(sportsMan);
	}

	//ApplyForRoleConfirmed
	public void applyForConfirmedRole(SportsMan sportsMan){
		PromotionRequest promotionRequest = new PromotionRequest(sportsMan,findConfirmedRole());
		managementService.saveRequest(promotionRequest);
		newsService.returnApplicationNew(sportsMan,NewsType.APPLY_AS_CONFIRMED);
	}


	public void blockOrUnblock(SportsMan sportsMan, boolean status){
		sportsMan.setBlocked(status);
		this.saveUser(sportsMan);
	}


	public Iterable<SportsMan> selectAuthorityUsers() {
		return this.sportsManRepository.selectAuthorityUsers(findAdministrator());
	}

	//FindSpecificConfirmedRole
	public Role findSimplyRole(){
		return this.roleRepository.find((long) 1);
	}

	public Role findConfirmedRole(){
		return this.roleRepository.find((long) 2);
	}

	public Role findAdministrator(){
		return this.roleRepository.find((long) 3);
	}

	public void createUser(SportsManForm sportsManForm){
		SportsMan sportsMan = new SportsMan(sportsManForm);
		sportsMan.setLevel(findBeginner());
		sportsMan.addRoles(findSimplyRole());
		sportsMan.setPassword(passwordEncoder.encode(sportsManForm.getPassword()));
		this.saveUser(sportsMan);
	}

	public void updateUser(SportsMan sportsMan, SportsManForm sportsManForm){
		sportsMan.updateSportsMan(sportsManForm);
		this.saveUser(sportsMan);
	}

	public void promoteUser(SportsMan sportsMan){
		sportsMan.addRoles(this.findConfirmedRole());
		this.saveUser(sportsMan);
	}





	// A valider!!!!!!!


	//SaveStatistic
	public void saveStatistic(Statistic statistic){
		this.statisticRepository.save(statistic);
	}

	//FindSpecificLevel
	public Level findSpecificLevel(Long id){
		return this.levelRepository.findSpecific(id);
	}

	public Level findBeginner(){
		return this.levelRepository.findSpecific((long) 1);
	}

	//FindAllRole
	public List<Role> findRoles(Long id){
		return this.roleRepository.findForInitialize(id);
	}



}
