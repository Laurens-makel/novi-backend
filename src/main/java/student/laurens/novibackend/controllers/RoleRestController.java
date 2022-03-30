package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Rest Controller that exposes CRUD methods for {@link Role}.
 *
 * Admins
 * <ul>
 *     <li>GET /roles</li>
 *     <li>POST /roles</li>
 *     <li>PUT /roles/{roleId}</li>
 *     <li>DELETE /roles/{roleId}</li>
 * </ul>
 *
 * Users, Content Creators, Moderators
 *
 * <ul>
 *     <li>GET /roles<</li>
 * </ul>
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/roles")
public class RoleRestController extends BaseRestController<Role> {

    private @Getter RoleService service;

    public RoleRestController(AppUserDetailsService appUserDetailsService, RoleService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        return create(role);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable Integer roleId, @RequestBody Role role) throws ResourceNotFoundException{
        return update(roleId, role);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity deleteRole(@PathVariable Integer roleId) throws ResourceNotFoundException {
        return delete(roleId);
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResourceByName(String name, Role resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("delete", linkTo(methodOn(RoleRestController.class).deleteRole(resource.getId())));
        links.put("update", linkTo(methodOn(RoleRestController.class).updateRole(resource.getId(), resource)));

        return links;
    }

    @Override
    protected Map<String, ControllerLinkBuilder> getLinksForGetResource(Integer resourceId, Role resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("delete", linkTo(methodOn(RoleRestController.class).deleteRole(resource.getId())));
        links.put("update", linkTo(methodOn(RoleRestController.class).updateRole(resource.getId(), resource)));

        return links;
    }
}
