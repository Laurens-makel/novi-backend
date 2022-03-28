package student.laurens.novibackend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.laurens.novibackend.entities.Comment;

public interface CommentRepository extends ResourceRepository<Comment> {
    @Query("SELECT c FROM Comment c WHERE c.title = :commentTitle")
    public Comment getCommentByTitle(@Param("commentTitle") String commentTitle);
}
