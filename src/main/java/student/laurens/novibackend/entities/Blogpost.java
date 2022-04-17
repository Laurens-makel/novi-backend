package student.laurens.novibackend.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for BLOGPOSTS table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "BLOGPOSTS")
@NoArgsConstructor
@AllArgsConstructor
public class Blogpost extends AbstractOwnedEntity implements Serializable {

    @Id
    @Column(name = "BLOGPOST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @ManyToOne( cascade = CascadeType.MERGE )
    @JoinColumn( name = "UID", nullable = false)
    private @Getter @Setter User author;

    @Column(name = "TITLE", nullable = false)
    private @Getter @Setter String title;

    @Column(name = "CONTENT", nullable = false)
    private @Getter @Setter String content;

    @Column(name = "PUBLISHED", nullable = false)
    private @Getter @Setter boolean published;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable=false)
    private @Getter Date createdAt;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "BLOGPOSTS_TAGS",
            joinColumns = @JoinColumn(name = "BLOGPOST_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID")
    )
    private @Getter Set<Tag> tags = new HashSet<>();

    @Override
    public Integer getOwnerUid() {
        return getAuthor().getId();
    }

}
