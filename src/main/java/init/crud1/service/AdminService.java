package init.crud1.service;

import init.crud1.entity.Role;
import init.crud1.entity.SportsMan;
import init.crud1.repository.RoleRepository;
import init.crud1.repository.SportsManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    SportsManRepository sportsManRepository;
    @Autowired
    RoleRepository roleRepository;

    public Iterable<SportsMan> selectAuthorityUsers() {
        return this.sportsManRepository.selectAuthorityUsers(findAdministrator());
    }

    public Role findAdministrator(){
        return this.roleRepository.find((long) 3);
    }


}
