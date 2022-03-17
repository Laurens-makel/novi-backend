package student.laurens.novibackend.services;

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
import java.util.Optional;

@Service
@Component
@Transactional
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new AppUserDetails(user);
    }

    public Iterable<User> listAll(){
        return repository.findAll();
    }

    public void addUser(User user) {
        repository.save(user);
    }

    public boolean removeUserById(Integer uid) {
        Optional<User> user = repository.findById(uid);

        if(user.isPresent()){
            repository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }

    public void updateUser(User user) {
        repository.save(user);
    }
}
