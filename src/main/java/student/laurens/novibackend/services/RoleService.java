package student.laurens.novibackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.repositories.RoleRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Transactional service that takes care of interactions with {@link RoleRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Service
@Component
@Transactional
public class RoleService extends BaseService<Role> {

    @Autowired
    private RoleRepository repository;

    public Role getResource(String roleName){
        return repository.getRoleByName(roleName);
    }

    public void createResource(Role role) {
        repository.save(role);
    }

    public void updateResourceById(Integer roleId, Role role) throws RoleNotFoundException {
        Optional<Role> found = repository.findById(roleId);

        if(!found.isPresent()){
            throw new RoleNotFoundException(roleId);
        }

        repository.save(role);
    }

    public void deleteResourceById (Integer roleId) throws RoleNotFoundException {
        Optional<Role> role = repository.findById(roleId);

        if(!role.isPresent()){
            throw new RoleNotFoundException(roleId);
        }

        repository.delete(role.get());
    }

    public Iterable<Role> getResource(){
        return repository.findAll();
    }
}
