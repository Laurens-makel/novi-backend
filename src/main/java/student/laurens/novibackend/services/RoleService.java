package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.repositories.RoleRepository;

import javax.transaction.Transactional;

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

    public RoleService(RoleRepository repository){
        this.repository = repository;
    }

    @Autowired
    private final @Getter RoleRepository repository;

    public Role getResource(final String roleName){
        return repository.getRoleByName(roleName);
    }

    @Override
    public Class<Role> getResourceClass() {
        return Role.class;
    }

}
