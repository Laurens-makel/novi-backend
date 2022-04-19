package student.laurens.novibackend.unit.dto.mappers;

import lombok.Getter;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.entities.dto.TagDto;
import student.laurens.novibackend.entities.dto.mappers.TagMapper;

import static org.junit.Assert.assertEquals;

public class TagMapperTest extends ResourceMapperTest<Tag, TagDto> {
    private @Getter TagMapper mapper = new TagMapper();

    @Override
    protected TagDto dto() {
        TagDto dto = new TagDto();
        dto.setTitle("My role");
        return dto;
    }

    @Override
    protected Tag entity() {
        Tag entity = new Tag();
        entity.setId(1);
        entity.setTitle("Tag");
        return entity;
    }

    @Override
    protected void assertToEntity(TagDto dto, Tag entity) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
    }

    @Override
    protected void assertToDto(Tag entity, TagDto dto) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
    }
}
