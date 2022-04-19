package student.laurens.novibackend.unit.dto.mappers;

import lombok.Getter;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.CommentDto;
import student.laurens.novibackend.entities.dto.mappers.CommentMapper;

import static org.junit.Assert.assertEquals;

public class CommentMapperTest extends ResourceMapperTest<Comment, CommentDto> {
    private @Getter CommentMapper mapper = new CommentMapper();

    @Override
    protected CommentDto dto() {
        CommentDto dto = new CommentDto();

        dto.setId(1);
        dto.setAuthor(1);
        dto.setBlogpost(1);
        dto.setTitle("My comment");
        dto.setContent("Hahaha lol");

        return dto;
    }

    @Override
    protected Comment entity() {
        Comment comment = new Comment();

        comment.setId(1);
        comment.setTitle("My comment");
        comment.setContent("Hahaha lol");

        User author = new User();
        author.setId(1);
        author.setUsername("Username");
        author.setFirstName("Henk");
        author.setLastName("Jan");
        author.setPassword("123");

        comment.setAuthor(author);

        Blogpost blogpost = new Blogpost();

        blogpost.setId(1);
        blogpost.setAuthor(author);
        blogpost.setPublished(true);
        blogpost.setTitle("post");
        blogpost.setContent("whatever");

        comment.setBlogpost(blogpost);

        return comment;
    }

    @Override
    protected void assertToEntity(CommentDto dto, Comment entity) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getAuthor().getId(), dto.getAuthor());
        assertEquals(entity.getBlogpost().getId(), dto.getBlogpost());
    }

    @Override
    protected void assertToDto(Comment entity, CommentDto dto) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getAuthor(), entity.getAuthor().getId());
        assertEquals(dto.getBlogpost(), entity.getBlogpost().getId());
    }
}
