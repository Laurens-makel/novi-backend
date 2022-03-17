package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.laurens.novibackend.entities.Blogpost;
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
public class BlogpostRestController {

    @Autowired
    private BlogpostService service;

    @GetMapping("/blogposts")
    public ResponseEntity<Iterable<Blogpost>> getBlogpost() {
        return new ResponseEntity<>(service.listAllPublished(), HttpStatus.OK);
    }
}
