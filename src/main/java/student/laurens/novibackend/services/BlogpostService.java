package student.laurens.novibackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.repositories.BlogpostRepository;

import javax.transaction.Transactional;

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
}
