package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.AppUserDetails;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.UserNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;

import java.security.Principal;

/**
 * Rest Controller that exposes CRUD methods for {@link User}.
 *
 * Admins
 * <ul>
 *     <li>GET /user</li>
 *     <li>GET /users</li>
 *     <li>POST /users</li>
 *     <li>PUT /users/{uid}</li>
 *     <li>DELETE /users/{uid}</li>
 * </ul>
 *
 * Moderators
 * <ul>
 *      <li>GET /user</li>
 *      <li>GET /users</li>
 * </ul>
 *
 * Users, Content Creators
 *
 * <ul>
 *     <li>GET /user</li>
 * </ul>
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
public class UserRestController extends ResourceBaseRestController<User>{

    private final @Getter AppUserDetailsService service;

    public UserRestController(final AppUserDetailsService appUserDetailsService) {
        super(appUserDetailsService);
        this.service = appUserDetailsService;
    }

    @GetMapping("/user")
    public ResponseEntity<AppUserDetails> GET(final Principal principal) {
        String username = principal.getName();
        logProcessingStarted(HttpMethod.GET, username);

        AppUserDetails userDetails = (AppUserDetails) service.loadUserByUsername(username);

        logProcessingFinished(HttpMethod.GET, username);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Resources<User>> GET() {
        return getResources();
    }

    @GetMapping("/users/{uid}")
    public ResponseEntity<Resource<User>> GET(@PathVariable final Integer uid) {
        return get(uid);
    }

    @PostMapping("/users")
    public ResponseEntity<Resource<User>> POST(@RequestBody final User user) throws ResourceDuplicateException {
        return create(user);
    }

    @PutMapping("/users/{uid}")
    public ResponseEntity<Resource<User>> PUT(@PathVariable final Integer uid, @RequestBody final User user) throws UserNotFoundException {
        return update(uid, user);
    }

    @DeleteMapping("/users/{uid}")
    public ResponseEntity DELETE(@PathVariable final Integer uid) throws UserNotFoundException {
        return delete(uid);
    }

    @GetMapping("/password")
    public ResponseEntity<String> getEncodedPassord(@RequestParam final String value) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new ResponseEntity<>(passwordEncoder.encode(value), HttpStatus.OK);
    }

}
