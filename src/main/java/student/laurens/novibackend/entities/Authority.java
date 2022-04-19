package student.laurens.novibackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class for AUTHORITIES table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@Table(name = "AUTHORITIES")
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @Column(name = "AUTHORITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(name = "AUTHORITY_NAME")
    private @Getter @Setter String name;
}
