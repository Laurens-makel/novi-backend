package student.laurens.novibackend.entities.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BlogpostDto extends ResourceDto {
    private Integer author;
    private String title;
    private String content;
    private Date createdAt;
}
