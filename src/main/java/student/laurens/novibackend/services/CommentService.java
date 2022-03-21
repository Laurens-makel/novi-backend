package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.repositories.CommentRepository;

import javax.transaction.Transactional;

@Service
@Component
@Transactional
public class CommentService extends BaseService<Comment> {

    @Autowired
    private @Getter CommentRepository repository;

    @Override
    public Comment getResource(String string) {
        return null;
    }

    @Override
    public Class<Comment> getResourceClass() { return Comment.class; }
}
