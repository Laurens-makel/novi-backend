package student.laurens.novibackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.AppUserDetails;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.UserNotFoundException;
import student.laurens.novibackend.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Transactional service that takes care of interactions with {@link UserRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Service
@Component
@Transactional
public class AppUserDetailsService extends BaseService<User> implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public User getResource(String username) throws UsernameNotFoundException {
        User user = repository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return user;
    }

    public Iterable<User> getResource(){
        return repository.findAll();
    }

    public void createResource(User user) {
        repository.save(user);
    }

    public void deleteResourceById(Integer uid) throws UserNotFoundException {
        Optional<User> user = repository.findById(uid);

        if(!user.isPresent()){
            throw new UserNotFoundException(uid);
        }

        repository.delete(user.get());
    }

    public void updateResourceById(Integer uid, User user) {
        Optional<User> current = repository.findById(uid);

        // TODO: Check user.getUid() == uid.

        if(!current.isPresent()){
            throw new UserNotFoundException(uid);
        }

        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new AppUserDetails(getResource(s));
    }
}
