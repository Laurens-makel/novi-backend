package student.laurens.novibackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
@Table(name = "BLOGPOSTS")
public class Blogpost extends AbstractOwnedParentEntity<Comment> {

    @Id
    @Column(name = "BLOGPOST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Integer id;

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
        return getAuthor().getUid();
    }

    @Override
    public PermissionPolicy isReadOnChildPermitted(User user, Comment childResource) {
        return PermissionPolicy.ALLOW;
    }

    @Override
    public PermissionPolicy isCreateChildPermitted(User user, Comment childResource) {
        return PermissionPolicy.ALLOW;
    }

    @Override
    public PermissionPolicy isUpdateOnChildPermitted(User user, Comment childResource) {
        if(user.hasRole("ADMIN") || user.hasRole("MODERATOR")){
            return PermissionPolicy.ALLOW;
        }
        return PermissionPolicy.ALLOW_CHILD_OWNED;
    }

    @Override
    public PermissionPolicy isDeleteOnChildPermitted(User user, Comment childResource) {
        if(user.hasRole("ADMIN") || user.hasRole("MODERATOR") ){
            return PermissionPolicy.ALLOW;
        }
        if(user.hasRole("CONTENT_CREATOR")){
            return PermissionPolicy.ALLOW_PARENT_OR_CHILD_OWNED;
        }
        return PermissionPolicy.ALLOW_CHILD_OWNED;
    }
}
