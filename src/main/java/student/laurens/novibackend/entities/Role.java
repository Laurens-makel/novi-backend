package student.laurens.novibackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class for ROLES table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Integer id;

    @Column(name = "ROLE_NAME", nullable = false, unique = true)
    private @Getter @Setter String name;

}
