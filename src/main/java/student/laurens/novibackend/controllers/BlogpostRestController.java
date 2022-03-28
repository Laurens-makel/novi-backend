package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.laurens.novibackend.dto.BlogpostDto;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.BlogpostService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
public class BlogpostRestController extends BaseRestController<Blogpost> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private @Getter BlogpostService service;

    @GetMapping("/{blogpostId}")
    public ResponseEntity<Blogpost> getBlogpost(@PathVariable Integer blogpostId) {
        return get(blogpostId);
    }

    @GetMapping
    public ResponseEntity<List<BlogpostDto>> getBlogposts() {
        List<BlogpostDto> posts = StreamSupport.stream(getService().getResources().spliterator(), false)
                .map(user -> modelMapper.map(user, BlogpostDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Blogpost> addBlogpost(@RequestBody Blogpost blogpost) {
        return create(blogpost);
    }

    @PutMapping("/{blogpostId}")
    public ResponseEntity<Blogpost> updateBlogpost(@PathVariable Integer blogpostId, @RequestBody Blogpost blogpost) throws ResourceNotFoundException {
        return update(blogpostId, blogpost);
    }

    @DeleteMapping("/{blogpostId}")
    public ResponseEntity deleteBlogpost(@PathVariable Integer blogpostId) throws ResourceNotFoundException {
        return delete(blogpostId);
    }
}
