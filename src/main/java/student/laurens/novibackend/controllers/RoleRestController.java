package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
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
public class RoleRestController extends BaseRestController {

    @Autowired
    private @Getter RoleService service;

    @GetMapping("/roles")
    public ResponseEntity<Iterable<Role>> getRoles() {
        return get();
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        return create(role);
    }

    @PutMapping("/roles/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable("roleId") Integer roleId, @RequestBody Role role){
        return update(roleId, role);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity deleteRole(@PathVariable("roleId") Integer roleId) throws RoleNotFoundException {
        return delete(roleId);
    }

}
