package student.laurens.novibackend.dto;

import lombok.Data;
import student.laurens.novibackend.entities.Tag;

import java.util.Date;
import java.util.Set;

@Data
public class BlogpostDto {
    private Integer id;
    private Integer authorId;
    private String title;
    private String content;
    private boolean published;
    private Date createdAt;
    private Set<Tag> tags;
}
