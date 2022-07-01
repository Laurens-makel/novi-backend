package student.laurens.novibackend;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import student.laurens.novibackend.services.AppUserDetailsService;

/**
 * Web Security Configuration class
 *
 * Defines all authentication rules for exposed web services.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/blogposts").hasAuthority("BLOGPOSTS_WRITE")
                .antMatchers(HttpMethod.PUT,"/blogposts/{blogpostId}").hasAnyAuthority("BLOGPOSTS_UPDATE_OWNED","BLOGPOSTS_UPDATE_NOT_OWNED")
                .antMatchers(HttpMethod.DELETE,"/blogposts/{blogpostId}").hasAnyAuthority("BLOGPOSTS_DELETE_OWNED","BLOGPOSTS_DELETE_NOT_OWNED")

                .antMatchers(HttpMethod.DELETE,"/blogposts/{blogpostId}/comments/{commentId}").hasAnyAuthority("COMMENTS_DELETE_OWNED","COMMENTS_DELETE_NOT_OWNED", "COMMENTS_DELETE_OWNED_OR_PARENT_OWNED")

                .antMatchers(HttpMethod.GET,"/users").hasAuthority("USERS_READ_ALL")
                .antMatchers(HttpMethod.POST,"/users").hasAuthority("USERS_WRITE")
                .antMatchers(HttpMethod.DELETE, "/users/{uid}").hasAnyAuthority("USERS_DELETE_OWNED","USERS_DELETE_NOT_OWNED")

                .antMatchers(HttpMethod.POST, "/roles").hasAuthority("ROLES_WRITE")
                .antMatchers(HttpMethod.DELETE, "/roles/{roleId}").hasAuthority("ROLES_DELETE")
                .antMatchers(HttpMethod.PUT, "/roles/{roleId}").hasAuthority("ROLES_UPDATE")

                .antMatchers(HttpMethod.POST, "/tags").hasAuthority("TAGS_WRITE")
                .antMatchers(HttpMethod.DELETE, "/tags/{roleId}").hasAuthority("TAGS_DELETE")
                .antMatchers(HttpMethod.PUT, "/tags/{roleId}").hasAuthority("TAGS_UPDATE")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll().deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();
    }

}
