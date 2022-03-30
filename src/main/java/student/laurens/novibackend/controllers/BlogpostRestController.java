package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BlogpostService;

import java.util.List;

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
    public ResponseEntity<Blogpost> getBlogpost(@PathVariable Integer blogpostId) {
        return get(blogpostId);
    }

    @GetMapping
    public ResponseEntity<List<Blogpost>> getBlogposts() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Blogpost> addBlogpost(@RequestBody Blogpost blogpost) {
        return create(blogpost);
    }

    @PutMapping("/{blogpostId}")
    public ResponseEntity<Blogpost> updateBlogpost(@PathVariable Integer blogpostId, @RequestBody Blogpost blogpost) throws ResourceNotFoundException {
        return update(blogpostId, blogpost);
    }

    @DeleteMapping("/{blogpostId}")
    public ResponseEntity deleteBlogpost(@PathVariable Integer blogpostId) throws ResourceNotFoundException {
        return delete(blogpostId);
    }
}
