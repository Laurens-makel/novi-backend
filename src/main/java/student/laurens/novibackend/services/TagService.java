package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.repositories.TagRepository;

import javax.transaction.Transactional;

@Service
@Component
@Transactional
public class TagService extends BaseService<Tag> {

    @Autowired
    private @Getter TagRepository repository;

    public Tag getResource(String title) {
        return repository.getTagByTitle(title);
    }

    @Override
    public Class<Tag> getResourceClass() {
        return Tag.class;
    }

}
