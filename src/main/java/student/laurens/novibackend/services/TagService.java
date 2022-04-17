package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.repositories.TagRepository;

import javax.transaction.Transactional;

@Service
@Component
@Transactional
public class TagService extends ResourceBaseService<Tag> {

    public TagService(TagRepository repository){
        this.repository = repository;
    }

    @Autowired
    private final @Getter TagRepository repository;

    public Tag getResource(final String title) {
        return repository.getTagByTitle(title);
    }

    @Override
    public Class<Tag> getResourceClass() {
        return Tag.class;
    }

}
