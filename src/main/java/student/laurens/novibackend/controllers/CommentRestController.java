package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public ResponseEntity<Resource<Comment>> getComments(@PathVariable Integer blogpostId, @PathVariable Integer commentId) throws ResourceNotFoundException {
        return get(blogpostId, commentId);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer blogpostId) throws ResourceNotFoundException {
        return getResources(blogpostId);
    }

    @PostMapping
    public ResponseEntity<Resource<Comment>> addComment(@PathVariable Integer blogpostId, @RequestBody Comment comment) throws ResourceNotFoundException, ResourceForbiddenException {
        return create(blogpostId, comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Resource<Comment>> updateComment(@PathVariable Integer blogpostId, @PathVariable Integer commentId, @RequestBody Comment comment) throws ResourceNotFoundException, ResourceForbiddenException {
        return update(blogpostId, commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer blogpostId, @PathVariable Integer commentId) throws ResourceNotFoundException, ResourceForbiddenException {
        return delete(blogpostId, commentId);
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResourceByName(final String name, final Comment resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("blogpost", linkTo(methodOn(BlogpostRestController.class).get(resource.getParentId())));
        links.put("delete", linkTo(methodOn(CommentRestController.class).deleteComment(resource.getParentId(), resource.getId())));
        links.put("update", linkTo(methodOn(CommentRestController.class).updateComment(resource.getParentId(), resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResource(final Integer resourceId, final Comment resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("blogpost", linkTo(methodOn(BlogpostRestController.class).get(resource.getParentId())));
        links.put("delete", linkTo(methodOn(CommentRestController.class).deleteComment(resource.getParentId(), resource.getId())));
        links.put("update", linkTo(methodOn(CommentRestController.class).updateComment(resource.getParentId(), resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForPostResource(Comment resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("blogpost", linkTo(methodOn(BlogpostRestController.class).get(resource.getParentId())));
        links.put("delete", linkTo(methodOn(CommentRestController.class).deleteComment(resource.getParentId(), resource.getId())));
        links.put("update", linkTo(methodOn(CommentRestController.class).updateComment(resource.getParentId(), resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForPutResource(Comment resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("blogpost", linkTo(methodOn(BlogpostRestController.class).get(resource.getParentId())));
        links.put("delete", linkTo(methodOn(CommentRestController.class).deleteComment(resource.getParentId(), resource.getId())));

        return links;
    }
}
