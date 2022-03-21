package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.services.CommentService;

@RestController
@RequestMapping("/blogposts/{blogpostId}/comments")
public class CommentRestController extends BaseRestController<Comment> {

    @Autowired
    private @Getter CommentService service;

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComments(@PathVariable String blogpostId, @PathVariable("commentId") Integer commentId) {
        return get(commentId);
    }

    @GetMapping
    public ResponseEntity<Iterable<Comment>> getComments(@PathVariable String blogpostId) {
        return get();
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable String blogpostId, @RequestBody Comment comment){
        return create(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable String blogpostId, @PathVariable("commentId") Integer commentId, @RequestBody Comment comment){
        return update(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable String blogpostId, @PathVariable("commentId") Integer commentId) throws RoleNotFoundException {
        return delete(commentId);
    }
}
