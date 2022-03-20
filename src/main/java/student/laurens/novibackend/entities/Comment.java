package student.laurens.novibackend.entities;

import lombok.Getter;
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
@Table(name = "COMMENTS")
public class Comment extends AbstractEntity {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Integer id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "AUTHOR_UID", referencedColumnName = "UID")
    private @Getter @Setter User author;

    @Column(name = "TITLE", nullable = false)
    private @Getter @Setter String title;

    @Column(name = "CONTENT", nullable = false)
    private @Getter @Setter String content;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable=false)
    private @Getter Date createdAt;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "BLOGPOST_ID", referencedColumnName = "BLOGPOST_ID")
    private @Getter @Setter Blogpost blogpost;
}
