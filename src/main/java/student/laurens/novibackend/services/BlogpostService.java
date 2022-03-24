package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.BlogpostRepository;

import javax.transaction.Transactional;

/**
 * Transactional service that takes care of interactions with  {@link BlogpostRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Service
@Component
@Transactional
@Qualifier("BlogpostService")
public class BlogpostService extends ParentBaseService<Blogpost> {

    @Autowired
    private @Getter BlogpostRepository repository;

    @Override
    public Iterable<Blogpost> getResources() {
        return repository.findAllPublished();
    }

    @Override
    public Class<Blogpost> getResourceClass() {
        return Blogpost.class;
    }

    public Blogpost getResource(final String title){
        return repository.findByTitle(title);
    }

    @Override
    public PermissionPolicy isReadOnChildPermitted(User user) {
        return PermissionPolicy.ALLOW;
    }

    @Override
    public PermissionPolicy isCreateChildPermitted(User user) {
        return PermissionPolicy.ALLOW;
    }

    @Override
    public PermissionPolicy isUpdateOnChildPermitted(User user) {
        if(user.hasRole("ADMIN") || user.hasRole("MODERATOR")){
            return PermissionPolicy.ALLOW;
        }
        return PermissionPolicy.ALLOW_CHILD_OWNED;
    }

    @Override
    public PermissionPolicy isDeleteOnChildPermitted(User user) {
        if(user.hasRole("ADMIN") || user.hasRole("MODERATOR") ){
            return PermissionPolicy.ALLOW;
        }
        if(user.hasRole("CONTENT_CREATOR")){
            return PermissionPolicy.ALLOW_PARENT_OR_CHILD_OWNED;
        }
        return PermissionPolicy.ALLOW_CHILD_OWNED;
    }

}
