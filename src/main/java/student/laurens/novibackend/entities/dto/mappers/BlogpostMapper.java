package student.laurens.novibackend.entities.dto.mappers;

import org.modelmapper.TypeMap;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.dto.BlogpostDto;

public class BlogpostMapper extends ResourceMapper<Blogpost, BlogpostDto> {

    @Override
    protected TypeMap<BlogpostDto, Blogpost> populateToEntityTypeMap(TypeMap<BlogpostDto, Blogpost> typeMapper) {
        typeMapper.addMappings(mapper -> mapper
                .using(authorEntityMapping)
                .map(
                        BlogpostDto::getAuthor,
                        Blogpost::setAuthor
                ));

        return typeMapper;
    }

    @Override
    protected TypeMap<Blogpost, BlogpostDto> populateToDtoTypeMap(TypeMap<Blogpost, BlogpostDto> typeMapper) {
        typeMapper.addMapping(src -> src.getAuthor().getId(), BlogpostDto::setAuthor);

        return typeMapper;
    }
}
