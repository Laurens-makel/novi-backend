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
                .antMatchers(HttpMethod.POST,"/blogposts").hasAnyRole("ADMIN", "CONTENT_CREATOR")
                .antMatchers(HttpMethod.PUT,"/blogposts/{blogpostId}").hasAnyRole("ADMIN", "CONTENT_CREATOR")
                .antMatchers(HttpMethod.DELETE,"/blogposts/{blogpostId}").hasAnyRole("ADMIN", "CONTENT_CREATOR")

                .antMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST,"/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/{uid}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{uid}").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/roles").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/roles/{roleId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/roles/{roleId}").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/tags").hasAnyRole("ADMIN", "CONTENT_CREATOR")
                .antMatchers(HttpMethod.DELETE, "/tags/{roleId}").hasAnyRole("ADMIN", "CONTENT_CREATOR")
                .antMatchers(HttpMethod.PUT, "/tags/{roleId}").hasAnyRole("ADMIN", "CONTENT_CREATOR")
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
