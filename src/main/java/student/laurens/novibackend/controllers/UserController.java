package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.users.AppUserDetails;
import student.laurens.novibackend.users.AppUserDetailsService;
import student.laurens.novibackend.users.User;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private AppUserDetailsService service;

    @GetMapping("/user")
    public AppUserDetails getUser(Principal principal) {
        return (AppUserDetails) service.loadUserByUsername(principal.getName());
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return service.listAll();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user){
        service.addUser(user);
    }

    @GetMapping("/password")
    public String getEncodedPassord(@RequestParam String value) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(value);
    }
}