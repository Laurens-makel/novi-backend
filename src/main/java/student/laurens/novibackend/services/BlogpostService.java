package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.BlogpostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
public class BlogpostService extends ParentResourceBaseService<Blogpost> {

    private final @Getter BlogpostRepository repository;

    public BlogpostService(BlogpostRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Blogpost> getResources() {
        Iterable<Blogpost> resourcesIterable = repository.findAllPublished();

        return StreamSupport.stream(resourcesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Class<Blogpost> getResourceClass() {
        return Blogpost.class;
    }

    public Blogpost getResource(final String title){
        return repository.findByTitle(title);
    }

    @Override
    public PermissionPolicy isReadOnChildPermitted(final User user) {
        return PermissionPolicy.ALLOW;
    }

    @Override
    public PermissionPolicy isCreateChildPermitted(final User user) {
        return PermissionPolicy.ALLOW;
    }

    @Override
    public PermissionPolicy isUpdateOnChildPermitted(final User user) {
        if(user.hasRole("ADMIN") || user.hasRole("MODERATOR")){
            return PermissionPolicy.ALLOW;
        }
        return PermissionPolicy.ALLOW_CHILD_OWNED;
    }

    @Override
    public PermissionPolicy isDeleteOnChildPermitted(final User user) {
        if(user.hasRole("ADMIN") || user.hasRole("MODERATOR") ){
            return PermissionPolicy.ALLOW;
        }
        if(user.hasRole("CONTENT_CREATOR")){
            return PermissionPolicy.ALLOW_PARENT_OR_CHILD_OWNED;
        }
        return PermissionPolicy.ALLOW_CHILD_OWNED;
    }

}
