package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.services.BlogpostService;

/**
 * Rest Controller that exposes CRUD methods for blogposts.
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
public class BlogpostRestController extends BaseRestController {
    @Autowired
    private BlogpostService service;

    @GetMapping("/blogposts")
    public ResponseEntity<Iterable<Blogpost>> getBlogpost() {
        return new ResponseEntity<>(service.listAllPublished(), HttpStatus.OK);
    }

    @PostMapping("/blogposts")
    public ResponseEntity<Blogpost> addBlogpost(@RequestBody Blogpost blogpost) {
        service.addBlogpost(blogpost);

        return new ResponseEntity<>(blogpost, HttpStatus.CREATED);
    }

    @PutMapping("/blogposts/{blogpostId}")
    public ResponseEntity<Blogpost> updateBlogpost(@PathVariable("blogpostId") Integer blogpostId, @RequestBody Blogpost blogpost) {
        service.updateBlogpostById(blogpostId, blogpost);

        return new ResponseEntity<>(blogpost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/blogposts/{blogpostId}")
    public ResponseEntity deleteRole(@PathVariable("blogpostId") Integer blogpostId) throws RoleNotFoundException {
        service.removeBlogpostById(blogpostId);

        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }
}
