package student.laurens.novibackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.exceptions.BlogpostNotFoundException;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.repositories.BlogpostRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Transactional service that takes care of interactions with BlogpostRepository.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Service
@Component
@Transactional
public class BlogpostService {

    @Autowired
    private BlogpostRepository repository;

    public Iterable<Blogpost> listAllPublished() {
        return repository.findAllPublished();
    }

    public Blogpost findByTitle(String title){
        return repository.findByTitle(title);
    }

    public void addBlogpost(Blogpost blogpost){
        repository.save(blogpost);
    }

    public void updateBlogpostById(Integer id, Blogpost blogpost) {
        Optional<Blogpost> found = repository.findById(id);

        if(!found.isPresent()){
            throw new BlogpostNotFoundException(id);
        }

        repository.save(blogpost);
    }

    public void removeBlogpostById(Integer id ) {
        Optional<Blogpost> found = repository.findById(id);

        if(!found.isPresent()){
            throw new BlogpostNotFoundException(id);
        }

        repository.delete(found.get());
    }
}
