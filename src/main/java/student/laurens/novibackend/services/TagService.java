package student.laurens.novibackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.exceptions.BlogpostNotFoundException;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.exceptions.TagNotFoundException;
import student.laurens.novibackend.repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Component
@Transactional
public class TagService {

    @Autowired
    private TagRepository repository;

    public Iterable<Tag> listAll() {
        return repository.findAll();
    }

    public void addTag(Tag tag) {
        repository.save(tag);
    }

    public Tag getTagByTitle(String title) {
        return repository.getTagByTitle(title);
    }

    public void updateTagById(Integer tagId, Tag tag) {
        Optional<Tag> found = repository.findById(tagId);

        if(!found.isPresent()){
            throw new TagNotFoundException(tagId);
        }

        repository.save(tag);
    }

    public void removeTagById(Integer tagId) {
        Optional<Tag> tag = repository.findById(tagId);

        if(!tag.isPresent()){
            throw new TagNotFoundException(tagId);
        }

        repository.delete(tag.get());
    }
}
