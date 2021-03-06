package student.laurens.novibackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for COMMENTS table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "COMMENTS")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractOwnedWithParentEntity<Blogpost> {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @ManyToOne( cascade = CascadeType.REFRESH )
    @JoinColumn( name = "UID", nullable = false)
    private @Getter @Setter User author;

    @Column(name = "TITLE", nullable = false)
    private @Getter @Setter String title;

    @Column(name = "CONTENT", nullable = false)
    private @Getter @Setter String content;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable=false)
    private @Getter Date createdAt;

    @ManyToOne( cascade = CascadeType.REFRESH )
    @JoinColumn( name = "BLOGPOST_ID", nullable = false)
    private @Getter @Setter Blogpost blogpost;

    @Override
    public Integer getOwnerUid() {
        return getAuthor().getId();
    }

    @Override
    protected Blogpost getParent() {
        return getBlogpost();
    }
}
