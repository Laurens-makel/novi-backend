package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.exceptions.BlogpostNotFoundException;
import student.laurens.novibackend.repositories.BlogpostRepository;
import student.laurens.novibackend.repositories.ResourceRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Transactional service that takes care of interactions with  {@link BlogpostRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Service
@Component
@Transactional
public class BlogpostService extends BaseService<Blogpost> {

    @Autowired
    private @Getter BlogpostRepository repository;

    @Override
    public Iterable<Blogpost> getResource() {
        return repository.findAllPublished();
    }

    public Blogpost getResource(String title){
        return repository.findByTitle(title);
    }

}
