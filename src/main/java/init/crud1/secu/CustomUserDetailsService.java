package init.crud1.secu;

import init.crud1.entity.SportsMan;
import init.crud1.repository.SportsManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SportsManRepository sportsManRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SportsMan sportsMan = sportsManRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
        if(sportsMan.getBlocked()){
            return null;// Trouver une exception!!!
        }else {
            return new org.springframework.security.core.userdetails.User(sportsMan.getEmail(), sportsMan.getPassword(),
                    getAuthorities(sportsMan));
        }
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(SportsMan sportsMan) {
        String[] userRoles = sportsMan.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

}

