package student.laurens.novibackend.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.repositories.CommentRepository;

import javax.transaction.Transactional;

@Service
@Component
@Transactional
public class CommentService extends ChildResourceBaseService<Comment, Blogpost> {

    public CommentService(CommentRepository repository){
        this.repository = repository;
    }

    @Autowired
    private final @Getter CommentRepository repository;

    @Override
    public Comment getResource(String title) {
        return repository.getCommentByTitle(title);
    }

    @Override
    public Class<Comment> getResourceClass() { return Comment.class; }

    @Autowired
    @Qualifier("BlogpostService")
    private @Getter
    BlogpostService parentService;

}
