package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.exceptions.RoleNotFoundException;
import student.laurens.novibackend.services.TagService;

@RestController
public class TagRestController extends BaseRestController {

    @Autowired
    private TagService service;

    @GetMapping("/tags")
    public ResponseEntity<Iterable<Tag>> getTags() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }


    @PostMapping("/tags")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag){
        service.addTag(tag);

        return new ResponseEntity<>(service.getTagByTitle(tag.getTitle()), HttpStatus.CREATED);
    }

    @PutMapping("/tags/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable("tagId") Integer tagId, @RequestBody Tag tag){
        service.updateTagById(tagId, tag);

        return new ResponseEntity<>(service.getTagByTitle(tag.getTitle()), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity deleteTag(@PathVariable("tagId") Integer tagId) throws RoleNotFoundException {
        service.removeTagById(tagId);

        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }
}
