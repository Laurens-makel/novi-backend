package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.CommentService;

import java.util.List;

/**
 * Rest Controller that exposes CRUD methods for {@link Comment}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/blogposts/{blogpostId}/comments")
public class CommentRestController extends ChildBaseRestController<Comment, Blogpost> {

    private @Getter CommentService service;

    public CommentRestController(AppUserDetailsService appUserDetailsService, CommentService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComments(@PathVariable Integer blogpostId, @PathVariable Integer commentId) throws ResourceNotFoundException {
        return get(blogpostId, commentId);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer blogpostId) throws ResourceNotFoundException {
        return getResources(blogpostId);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Integer blogpostId, @RequestBody Comment comment) throws ResourceNotFoundException, ResourceForbiddenException {
        return create(blogpostId, comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer blogpostId, @PathVariable Integer commentId, @RequestBody Comment comment) throws ResourceNotFoundException, ResourceForbiddenException {
        return update(blogpostId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer blogpostId, @PathVariable Integer commentId) throws ResourceNotFoundException, ResourceForbiddenException {
        return delete(blogpostId, commentId);
    }

}
