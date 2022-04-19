package student.laurens.novibackend.entities.dto.mappers;

import org.modelmapper.TypeMap;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.entities.dto.TagDto;

public class TagMapper extends ResourceMapper<Tag, TagDto>{
    @Override
    protected TypeMap<TagDto, Tag> populateToEntityTypeMap(TypeMap<TagDto, Tag> typeMapper) {
        return typeMapper;
    }

    @Override
    protected TypeMap<Tag, TagDto> populateToDtoTypeMap(TypeMap<Tag, TagDto> typeMapper) {
        return typeMapper;
    }
}
