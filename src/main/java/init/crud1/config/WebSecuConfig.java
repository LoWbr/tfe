package init.crud1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecuConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authprovider());
    }

    public AuthenticationProvider authprovider(){
        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
        impl.setUserDetailsService(customUserDetailsService);
        impl.setPasswordEncoder(passwordEncoder());
        impl.setHideUserNotFoundExceptions(false);
        return impl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/manage", "/user/block**", "/user/unblock**").hasRole("ADMINISTRATOR")
                .antMatchers("/create", "/event/update**", "/addUser**", "/removeUser**", "/activeStatus**", "/close**","/ownEvents","/ownEvent{id}").hasAnyRole("CONFIRMED", "ADMINISTRATOR")
                .antMatchers("/createComment", "/addComment", "/user/update**", "/user", "/addContact**", "/removeContact**").hasAnyRole("CONFIRMED", "ADMINISTRATOR", "SIMPLY")
                .antMatchers("/", "/events", "/event{id}", "/signIn", "/signUp", "/contactUs", "/about", "/search", "/users","/saveUser").permitAll()
                /*.anyRequest().authenticated()*/
                .and()
                .authorizeRequests()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin()
                .loginPage("/signIn")//Par défaut, redirection pour toute opération nécessitant une connexion
                .defaultSuccessUrl("/")
                /*.failureUrl("/signIn?error=true")*/
                .failureHandler(new MyAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("my-remember-me-cookie")
                .permitAll()
                .and()
                .rememberMe()
                .key("my-secure-key")
                .rememberMeCookieName("my-remember-me-cookie")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60)
                .and()
                .exceptionHandling()
        ;
    }

    private class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            String exceptionMessage = exception.getMessage();
            String encodedMsg = Base64.getUrlEncoder().encodeToString(exceptionMessage.getBytes());
            response.sendRedirect("/signIn?error=" + encodedMsg);
        }
    }

    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

}

