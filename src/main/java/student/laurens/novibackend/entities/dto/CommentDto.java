package student.laurens.novibackend.entities.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto extends ResourceDto {
    private Integer author;
    private Integer blogpost;
    private String title;
    private String content;
    private Date createdAt;
}
