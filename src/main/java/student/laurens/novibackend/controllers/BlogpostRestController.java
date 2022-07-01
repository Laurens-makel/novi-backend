package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.dto.BlogpostDto;
import student.laurens.novibackend.entities.dto.mappers.BlogpostMapper;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BlogpostService;

/**
 * Rest Controller that exposes CRUD methods for {@link Blogpost}.
 *
 * Admins
 * <ul>
 *     <li>GET /blogposts</li>
 * </ul>
 *
 * Users
 *
 * <ul>
 *     <li>GET /blogposts<</li>
 * </ul>
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RestController
@RequestMapping("/blogposts")
public class BlogpostRestController extends ResourceBaseRestController<Blogpost, BlogpostDto> {

    private final @Getter BlogpostMapper mapper = new BlogpostMapper();
    private final @Getter BlogpostService service;

    public BlogpostRestController(final AppUserDetailsService appUserDetailsService, final BlogpostService service) {
        super(appUserDetailsService);
        this.service = service;
    }

    @GetMapping("/{blogpostId}")
    public ResponseEntity<Resource<BlogpostDto>> GET(@PathVariable final Integer blogpostId) throws ResourceNotFoundException {
        return get(blogpostId);
    }

    @GetMapping
    public ResponseEntity<Resources<Blogpost>> GET() {
        return getResources();
    }

    @PostMapping
    public ResponseEntity<Resource<BlogpostDto>> POST(@RequestBody final BlogpostDto blogpost) throws ResourceDuplicateException {
        return create(blogpost);
    }

    @PutMapping("/{blogpostId}")
    public ResponseEntity<Resource<BlogpostDto>> PUT(@PathVariable final Integer blogpostId, @RequestBody final BlogpostDto blogpost) throws ResourceNotFoundException, ResourceForbiddenException {
        return update(blogpostId, blogpost);
    }

    @DeleteMapping("/{blogpostId}")
    public ResponseEntity DELETE(@PathVariable final Integer blogpostId) throws ResourceNotFoundException, ResourceForbiddenException {
        return delete(blogpostId);
    }

}
