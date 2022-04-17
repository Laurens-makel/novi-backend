package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.AppUserDetails;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.UserRepository;

import javax.transaction.Transactional;

/**
 * Transactional service that takes care of interactions with {@link UserRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Service
@Component
@Transactional
public class AppUserDetailsService extends ResourceBaseService<User> implements UserDetailsService {

    @Autowired
    private @Getter UserRepository repository;

    @Override
    public User getResource(String username) throws UsernameNotFoundException {
        User user = repository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return user;
    }

    @Override
    public Class<User> getResourceClass() {
        return User.class;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new AppUserDetails(getResource(s));
    }
}
