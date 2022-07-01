package student.laurens.novibackend.entities.dto.mappers;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.CommentDto;

public class CommentMapper extends ResourceMapper<Comment, CommentDto> {

    @Override
    protected TypeMap<CommentDto, Comment> populateToEntityTypeMap(TypeMap<CommentDto, Comment> typeMapper) {
        Converter<Integer, Blogpost> blogpost = mappingContext -> {
            Integer id = mappingContext.getSource();
            Blogpost blog = new Blogpost();
            blog.setId(id);
            return blog;
        };
        typeMapper.addMappings(mapper -> mapper
                .using(authorEntityMapping)
                .map(
                        CommentDto::getAuthor,
                        Comment::setAuthor
                )
        );
        typeMapper.addMappings(mapper -> mapper
                .using(blogpost)
                .map(
                        CommentDto::getBlogpost,
                        Comment::setBlogpost
                )
        );
        return typeMapper;
    }

    @Override
    protected TypeMap<Comment, CommentDto> populateToDtoTypeMap(TypeMap<Comment, CommentDto> typeMapper) {
        typeMapper.addMapping(src -> src.getAuthor().getId(), CommentDto::setAuthor);
        typeMapper.addMapping(src -> src.getBlogpost().getId(), CommentDto::setBlogpost);

        return typeMapper;
    }
}
