package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.services.BlogpostService;
import student.laurens.novibackend.services.CommentService;

/**
 * Rest Controller that exposes CRUD methods for {@link Comment}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/blogposts/{blogpostId}/comments")
public class CommentRestController extends ChildBaseRestController<Comment, Blogpost> {

    @Autowired
    private @Getter CommentService service;

    @Autowired
    @Qualifier("BlogpostService")
    private @Getter BlogpostService parentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComments(@PathVariable Integer blogpostId, @PathVariable Integer commentId) {
        return get(blogpostId, commentId);
    }

    @GetMapping
    public ResponseEntity<Iterable<Comment>> getComments(@PathVariable Integer blogpostId) {
        return getResources(blogpostId);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Integer blogpostId, @RequestBody Comment comment){
        return create(blogpostId, comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer blogpostId, @PathVariable Integer commentId, @RequestBody Comment comment){
        return update(blogpostId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer blogpostId, @PathVariable Integer commentId) throws RoleNotFoundException {
        return delete(blogpostId, commentId);
    }

}
