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

    @GetMapping
    public ResponseEntity<Iterable<Comment>> getComments(@PathVariable String blogpostId) {
        return get();
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable String blogpostId, @RequestBody Comment comment){
        return create(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateCommen(@PathVariable String blogpostI, @PathVariable("commentId") Integer commentId, @RequestBody Comment comment){
        return update(commentId, comment);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity deleteRole(@PathVariable String blogpostI, @PathVariable("commentId") Integer commentId) throws RoleNotFoundException {
        return delete(commentId);
    }
}
