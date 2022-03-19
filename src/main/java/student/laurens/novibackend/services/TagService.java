package student.laurens.novibackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.exceptions.TagNotFoundException;
import student.laurens.novibackend.repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Component
@Transactional
public class TagService extends BaseService<Tag> {

    @Autowired
    private TagRepository repository;

    public Iterable<Tag> getResource() {
        return repository.findAll();
    }

    public void createResource(Tag tag) {
        repository.save(tag);
    }

    public Tag getResource(String title) {
        return repository.getTagByTitle(title);
    }

    public void updateResourceById(Integer tagId, Tag tag) {
        Optional<Tag> found = repository.findById(tagId);

        if(!found.isPresent()){
            throw new TagNotFoundException(tagId);
        }

        repository.save(tag);
    }

    public void deleteResourceById(Integer tagId) {
        Optional<Tag> tag = repository.findById(tagId);

        if(!tag.isPresent()){
            throw new TagNotFoundException(tagId);
        }

        repository.delete(tag.get());
    }
}
