package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.AppUserDetails;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.services.BlogpostService;

import java.security.Principal;

/**
 * Rest Controller that exposes CRUD methods for blogposts.
 *
 * Admin
 * <ul>
 *     <li>GET /blogposts</li>
 *     <li>POST /blogposts</li>
 *     <li>PUT /blogposts/blogpostId</li>
 * </ul>
 *
 * Content Creator
 * <ul>
 *     <li>GET /blogposts</li>
 *     <li>POST /blogposts</li>
 *     <li>PUT /blogposts/blogpostId</li>
 * </ul>
 *
 * Moderator
 * <ul>
 *     <li>GET /blogposts<</li>
 * </ul>
 *
 * User
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

    @PostMapping("/blogposts")
    public ResponseEntity<Blogpost> addBlogpost(@RequestBody Blogpost blogpost, Principal principal) {
        AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(principal.getName());
        blogpost.setAuthor(userDetails.getUser());

        service.addBlogpost(blogpost);

        return new ResponseEntity<>(blogpost, HttpStatus.CREATED);
    }

    @PutMapping("/blogposts/{blogpostId}")
    public ResponseEntity<Blogpost> updateBlogpost(@PathVariable("blogpostId") Integer blogpostId, @RequestBody Blogpost blogpost) {
        service.updateBlogpost(blogpost);

        return new ResponseEntity<>(blogpost, HttpStatus.ACCEPTED);
    }
}
