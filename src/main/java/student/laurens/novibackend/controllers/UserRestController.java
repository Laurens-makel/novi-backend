package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.AppUserDetails;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.UserNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
public class UserRestController extends BaseRestController<User>{

    private @Getter AppUserDetailsService service;

    public UserRestController(AppUserDetailsService appUserDetailsService) {
        super(appUserDetailsService);
        this.service = appUserDetailsService;
    }

    @GetMapping("/user")
    public ResponseEntity<AppUserDetails> getUser(Principal principal) {
        String username = principal.getName();
        logProcessingStarted(HttpMethod.GET, username);

        AppUserDetails userDetails = (AppUserDetails) service.loadUserByUsername(username);

        logProcessingFinished(HttpMethod.GET, username);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return get();
    }

    @PostMapping("/users")
    public ResponseEntity<Resource<User>> addUser(@RequestBody User user){
        return create(user);
    }

    @PutMapping("/users/{uid}")
    public ResponseEntity<User> updateUser(@PathVariable Integer uid, @RequestBody User user) throws UserNotFoundException {
        return update(uid, user);
    }

    @DeleteMapping("/users/{uid}")
    public ResponseEntity deleteUser(@PathVariable Integer uid) throws UserNotFoundException {
        return delete(uid);
    }

    @GetMapping("/password")
    public ResponseEntity<String> getEncodedPassord(@RequestParam String value) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new ResponseEntity<>(passwordEncoder.encode(value), HttpStatus.OK);
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResourceByName(final String name, final User resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("delete", linkTo(methodOn(UserRestController.class).deleteUser(resource.getId())));
        links.put("update", linkTo(methodOn(UserRestController.class).updateUser(resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResource(final Integer resourceId, final User resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("delete", linkTo(methodOn(UserRestController.class).deleteUser(resource.getId())));
        links.put("update", linkTo(methodOn(UserRestController.class).updateUser(resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForPostResource(User resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("delete", linkTo(methodOn(UserRestController.class).deleteUser(resource.getId())));
        links.put("update", linkTo(methodOn(UserRestController.class).updateUser(resource.getId(), resource)));

        return links;
    }
}
