package student.laurens.novibackend.unit.dto.mappers;

import lombok.Getter;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.BlogpostDto;
import student.laurens.novibackend.entities.dto.mappers.BlogpostMapper;

import static org.junit.Assert.assertEquals;

public class BlogpostMapperTest extends ResourceMapperTest<Blogpost, BlogpostDto> {
    private @Getter BlogpostMapper mapper = new BlogpostMapper();

    @Override
    protected BlogpostDto dto() {
        BlogpostDto dto = new BlogpostDto();

        dto.setId(1);
        dto.setAuthor(1);
        dto.setTitle("My blogpost");
        dto.setContent("Hahaha lol");

        return dto;
    }

    @Override
    protected Blogpost entity() {
        Blogpost blogpost = new Blogpost();

        blogpost.setId(1);
        blogpost.setTitle("My blogpost");
        blogpost.setContent("Hahaha lol");

        User author = new User();
        author.setId(1);
        author.setUsername("Username");
        author.setFirstName("Henk");
        author.setLastName("Jan");
        author.setPassword("123");

        blogpost.setAuthor(author);

        return blogpost;
    }

    @Override
    protected void assertToEntity(BlogpostDto dto, Blogpost entity) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getAuthor().getId(), dto.getAuthor());
    }

    @Override
    protected void assertToDto(Blogpost entity, BlogpostDto dto) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getAuthor(), entity.getAuthor().getId());
    }
}
