package student.laurens.novibackend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Component
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role getRoleByName(String roleName){
        return repository.getRoleByName(roleName);
    }

    public void addRole(Role role) {
        repository.save(role);
    }

    public void updateRole(Role role){
        repository.save(role);
    }

    public boolean removeRoleById (Integer roleId) {
        Optional<Role> role = repository.findById(roleId);

        if(role.isPresent()){
            repository.delete(role.get());
            return true;
        } else {
            return false;
        }
    }

    public Iterable<Role> listAll(){
        return repository.findAll();
    }
}
