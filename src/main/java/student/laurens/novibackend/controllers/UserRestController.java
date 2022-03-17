package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.users.AppUserDetails;
import student.laurens.novibackend.users.AppUserDetailsService;
import student.laurens.novibackend.users.User;

import java.security.Principal;

@RestController
public class UserRestController {

    @Autowired
    private AppUserDetailsService service;

    @GetMapping("/user")
    public ResponseEntity<AppUserDetails> getUser(Principal principal) {
        AppUserDetails userDetails = (AppUserDetails) service.loadUserByUsername(principal.getName());

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<AppUserDetails> addUser(@RequestBody User user){
        service.addUser(user);

        AppUserDetails userDetails = (AppUserDetails) service.loadUserByUsername(user.getUsername());

        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }

    @GetMapping("/password")
    public ResponseEntity<String> getEncodedPassord(@RequestParam String value) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new ResponseEntity<>(passwordEncoder.encode(value), HttpStatus.OK);
    }
}
