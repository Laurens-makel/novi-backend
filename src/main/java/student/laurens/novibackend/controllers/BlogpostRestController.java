package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BlogpostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Rest Controller that exposes CRUD methods for {@link Blogpost}.
 *
 * Admins
 * <ul>
 *     <li>GET /blogposts</li>
 * </ul>
 *
 * Users
 *
 * <ul>
 *     <li>GET /blogposts<</li>
 * </ul>
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/blogposts")
public class BlogpostRestController extends BaseRestController<Blogpost> {

    private @Getter BlogpostService service;

    public BlogpostRestController(AppUserDetailsService appUserDetailsService, BlogpostService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping("/{blogpostId}")
    public ResponseEntity<Resource<Blogpost>> getBlogpost(@PathVariable Integer blogpostId) {
        return get(blogpostId);
    }

    @GetMapping
    public ResponseEntity<List<Blogpost>> getBlogposts() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Resource<Blogpost>> addBlogpost(@RequestBody Blogpost blogpost) {
        return create(blogpost);
    }

    @PutMapping("/{blogpostId}")
    public ResponseEntity<Resource<Blogpost>> updateBlogpost(@PathVariable Integer blogpostId, @RequestBody Blogpost blogpost) throws ResourceNotFoundException {
        return update(blogpostId, blogpost);
    }

    @DeleteMapping("/{blogpostId}")
    public ResponseEntity deleteBlogpost(@PathVariable Integer blogpostId) throws ResourceNotFoundException {
        return delete(blogpostId);
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResourceByName(final String name, final Blogpost resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("comments", linkTo(methodOn(CommentRestController.class).getComments(resource.getId())));
        links.put("delete", linkTo(methodOn(BlogpostRestController.class).deleteBlogpost(resource.getId())));
        links.put("update", linkTo(methodOn(BlogpostRestController.class).updateBlogpost(resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResource(final Integer resourceId, final Blogpost resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("comments", linkTo(methodOn(CommentRestController.class).getComments(resourceId)));
        links.put("delete", linkTo(methodOn(BlogpostRestController.class).deleteBlogpost(resourceId)));
        links.put("update", linkTo(methodOn(BlogpostRestController.class).updateBlogpost(resourceId, resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForPostResource(Blogpost resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("comments", linkTo(methodOn(CommentRestController.class).getComments(resource.getId())));
        links.put("delete", linkTo(methodOn(BlogpostRestController.class).deleteBlogpost(resource.getId())));
        links.put("update", linkTo(methodOn(BlogpostRestController.class).updateBlogpost(resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForPutResource(Blogpost resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("comments", linkTo(methodOn(CommentRestController.class).getComments(resource.getId())));
        links.put("delete", linkTo(methodOn(BlogpostRestController.class).deleteBlogpost(resource.getId())));

        return links;
    }
}
