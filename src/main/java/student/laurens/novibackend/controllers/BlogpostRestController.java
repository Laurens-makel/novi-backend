package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.exceptions.BlogpostNotFoundException;
import student.laurens.novibackend.services.BlogpostService;

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

    @Autowired
    private @Getter BlogpostService service;

    @GetMapping
    public ResponseEntity<Iterable<Blogpost>> getBlogpost() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Blogpost> addBlogpost(@RequestBody Blogpost blogpost) {
        return create(blogpost);
    }

    @PutMapping("/{blogpostId}")
    public ResponseEntity<Blogpost> updateBlogpost(@PathVariable("blogpostId") Integer blogpostId, @RequestBody Blogpost blogpost)  throws BlogpostNotFoundException {
        return update(blogpostId, blogpost);
    }

    @DeleteMapping("/{blogpostId}")
    public ResponseEntity deleteRole(@PathVariable("blogpostId") Integer blogpostId) throws BlogpostNotFoundException {
        return delete(blogpostId);
    }
}
