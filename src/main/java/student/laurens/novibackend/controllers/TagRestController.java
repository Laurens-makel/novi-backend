package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TagRestController extends BaseRestController<Tag> {

    private @Getter TagService service;

    public TagRestController(AppUserDetailsService appUserDetailsService, TagService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getTags() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag){
        return create(tag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable Integer tagId, @RequestBody Tag tag) throws ResourceNotFoundException {
        return update(tagId, tag);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity deleteTag(@PathVariable Integer tagId) throws ResourceNotFoundException {
        return delete(tagId);
    }
}
