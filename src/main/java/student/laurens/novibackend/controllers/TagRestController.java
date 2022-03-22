package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.services.TagService;

/**
 * Rest Controller that exposes CRUD methods for {@link Tag}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/tags")
public class TagRestController extends BaseRestController<Tag> {

    @Autowired
    private @Getter TagService service;

    @GetMapping
    public ResponseEntity<Iterable<Tag>> getTags() {
        return get();
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag){
        return create(tag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable("tagId") Integer tagId, @RequestBody Tag tag){
        return update(tagId, tag);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity deleteTag(@PathVariable("tagId") Integer tagId) throws RoleNotFoundException {
        return delete(tagId);
    }
}
