package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.dto.CommentDto;
import student.laurens.novibackend.entities.dto.mappers.CommentMapper;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.CommentService;

/**
 * Rest Controller that exposes CRUD methods for {@link Comment}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/blogposts/{blogpostId}/comments")
public class CommentRestController extends ChildBaseRestController<Comment, Blogpost, CommentDto> {

    private final @Getter CommentMapper mapper = new CommentMapper();
    private final @Getter CommentService service;

    public CommentRestController(AppUserDetailsService appUserDetailsService, CommentService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Resource<CommentDto>> GET(@PathVariable final Integer blogpostId, @PathVariable final Integer commentId) throws ResourceNotFoundException {
        return get(blogpostId, commentId);
    }

    @GetMapping
    public ResponseEntity<Resources<Comment>> GET(@PathVariable final Integer blogpostId) throws ResourceNotFoundException {
        return getResources(blogpostId);
    }

    @PostMapping
    public ResponseEntity<Resource<CommentDto>> POST(@PathVariable final Integer blogpostId, @RequestBody final CommentDto comment) throws ResourceNotFoundException, ResourceForbiddenException, ResourceDuplicateException {
        return create(blogpostId, comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Resource<CommentDto>> PUT(@PathVariable final Integer blogpostId, @PathVariable final Integer commentId, @RequestBody final CommentDto comment) throws ResourceNotFoundException, ResourceForbiddenException {
        return update(blogpostId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity DELETE(@PathVariable final Integer blogpostId, @PathVariable final Integer commentId) throws ResourceNotFoundException, ResourceForbiddenException {
        return delete(blogpostId, commentId);
    }

}
