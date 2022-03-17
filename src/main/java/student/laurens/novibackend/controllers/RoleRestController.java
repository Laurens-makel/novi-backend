package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import student.laurens.novibackend.users.Role;
import student.laurens.novibackend.users.RoleService;

import javax.websocket.server.PathParam;


@RestController
public class RoleRestController {

    @Autowired
    private RoleService service;

    @GetMapping("/roles")
    public ResponseEntity<Iterable<Role>> getRoles() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        service.addRole(role);

        return new ResponseEntity<>(service.getRoleByName(role.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/roles/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable("roleId") Integer roleId, @RequestBody Role role){
        service.updateRole(role);

        return new ResponseEntity<>(service.getRoleByName(role.getName()), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity deleteRole(@PathVariable("roleId") Integer roleId){
        if(service.removeRoleById(roleId)){
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
