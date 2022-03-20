package student.laurens.novibackend.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import student.laurens.novibackend.services.AppUserDetailsService;

@Component("ResourceSecurity")
public class ResourceSecurity {

    @Autowired
    private AppUserDetailsService service;

    public boolean isUser(Authentication authentication, Integer uid){
//        User user = asAppUser();

        return true;
    }

    private User asAppUser() {
        try{
            User user = service.getResource(SecurityContextHolder.getContext().getAuthentication().getName());
            return user;

        } catch (UsernameNotFoundException e) {

        }
        return null;

    }

    private boolean isAdmin(User user){
        return user.getRoles().stream().anyMatch( (role -> role.getName().equalsIgnoreCase("ADMIN")));
    }
}
