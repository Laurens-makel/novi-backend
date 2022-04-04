package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.RoleService;

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
public class RoleRestController extends ResourceBaseRestController<Role> {

    private final @Getter RoleService service;

    public RoleRestController(AppUserDetailsService appUserDetailsService, RoleService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Resources<Role>>  GET() {
        return getResources();
    }

    @GetMapping("/roleId")
    public ResponseEntity<Resource<Role>> GET(@PathVariable final Integer resourceId) {
        return get(resourceId);
    }

    @PostMapping
    public ResponseEntity<Resource<Role>> POST(@RequestBody final Role role){
        return create(role);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Resource<Role>> PUT(@PathVariable final Integer roleId, @RequestBody final Role role) throws ResourceNotFoundException{
        return update(roleId, role);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity DELETE(@PathVariable final Integer roleId) throws ResourceNotFoundException {
        return delete(roleId);
    }

}
