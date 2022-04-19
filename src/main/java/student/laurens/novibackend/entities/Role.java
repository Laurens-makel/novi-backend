package student.laurens.novibackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for ROLES table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@Table(name = "ROLES")
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity {

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(name = "ROLE_NAME", nullable = false, unique = true)
    private @Getter @Setter String name;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ROLE_AUTHORITIES",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID")
    )
    private @Getter Set<Authority> authorities = new HashSet<>();


}
