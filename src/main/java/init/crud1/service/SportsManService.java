package init.crud1.service;

import init.crud1.entity.Level;
import init.crud1.entity.Role;
import init.crud1.entity.SportsMan;
import init.crud1.entity.Statistic;
import init.crud1.repository.LevelRepository;
import init.crud1.repository.RoleRepository;
import init.crud1.repository.SportsManRepository;
import init.crud1.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportsManService {

    SportsManRepository sportsManRepository;
    StatisticRepository statisticRepository;
    LevelRepository levelRepository;
    RoleRepository roleRepository;

    @Autowired
    public SportsManService(SportsManRepository sportsManRepository, StatisticRepository statisticRepository,
                            LevelRepository levelRepository, RoleRepository roleRepository) {
        this.sportsManRepository = sportsManRepository;
        this.statisticRepository = statisticRepository;
        this.levelRepository = levelRepository;
        this.roleRepository = roleRepository;
    }

    //FindCurrentUser
    public SportsMan findCurrentUser(String mail){
        return this.sportsManRepository.findSpecific(mail);
    }

    //FindSpecificUser
    public SportsMan findSpecificUser(Long id){
        return this.sportsManRepository.findSpecific(id);
    }

    //AllUser
    public Iterable<SportsMan> getAllUser(){
        return this.sportsManRepository.findAll();
    }

    //AllExceptCurrent
    public Iterable<SportsMan> getAllExceptCurrent(Long id){
        return this.sportsManRepository.findAllWithoutMe(id);
    }

    //AllNotContacts
    public Iterable<SportsMan> getAllNoContats(List<SportsMan> contacts, Long id){
        return this.sportsManRepository.findNotContacts(contacts, id);
    }

    //SaveUser
    public void saveUser(SportsMan sportsMan){
        this.sportsManRepository.save(sportsMan);
    }

    //SaveStatistic
    public void saveStatistic(Statistic statistic){
        this.statisticRepository.save(statistic);
    }

    //FindbyUser
    public Iterable<Statistic> findBySportsMan(SportsMan sportsMan){
        return this.statisticRepository.findBySportsMan(sportsMan);
    }

    //FindSpecificLevel
    public Level findSpecificLevel(Long id){
        return this.levelRepository.findSpecific(id);
    }

    //FindAllRole
    public List<Role> findSpecificRole(Long id){
        return this.roleRepository.findForInitialize(id);
    }

    //FindSpecificRole
    public Role findRole(Long id){
        return this.roleRepository.findForPromotion(id);
    }

    public Iterable<SportsMan> findAuthority(Role role){
        return this.sportsManRepository.selectAuthorityUsers(role);
    }

}
