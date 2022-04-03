package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.TagService;

import java.util.List;

/**
 * Rest Controller that exposes CRUD methods for {@link Tag}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/tags")
public class TagRestController extends ResourceBaseRestController<Tag> {

    private final @Getter TagService service;

    public TagRestController(AppUserDetailsService appUserDetailsService, TagService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> GET() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Resource<Tag>> POST(@RequestBody Tag tag){
        return create(tag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Resource<Tag>> PUT(@PathVariable Integer tagId, @RequestBody Tag tag) throws ResourceNotFoundException {
        return update(tagId, tag);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity DELETE(@PathVariable Integer tagId) throws ResourceNotFoundException {
        return delete(tagId);
    }

}
