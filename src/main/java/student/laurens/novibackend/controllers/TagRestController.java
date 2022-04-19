package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.entities.dto.TagDto;
import student.laurens.novibackend.entities.dto.mappers.TagMapper;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.TagService;

/**
 * Rest Controller that exposes CRUD methods for {@link Tag}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/tags")
public class TagRestController extends ResourceBaseRestController<Tag, TagDto> {

    private final @Getter TagMapper mapper = new TagMapper();
    private final @Getter TagService service;

    public TagRestController(AppUserDetailsService appUserDetailsService, TagService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Resources<Tag>> GET() {
        return getResources();
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Resource<TagDto>> GET(@PathVariable final Integer tagId) {
        return get(tagId);
    }

    @PostMapping
    public ResponseEntity<Resource<TagDto>> POST(@RequestBody final TagDto tag) throws ResourceDuplicateException {
        return create(tag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Resource<TagDto>> PUT(@PathVariable final Integer tagId, @RequestBody final TagDto tag) throws ResourceNotFoundException {
        return update(tagId, tag);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity DELETE(@PathVariable final Integer tagId) throws ResourceNotFoundException {
        return delete(tagId);
    }

}
