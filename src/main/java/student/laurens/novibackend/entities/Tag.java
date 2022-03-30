package student.laurens.novibackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for TAGS table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@Table(name = "TAGS")
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AbstractEntity {

    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Integer id;

    @Column(name = "TITLE", nullable = false)
    private @Getter @Setter String title;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable=false)
    private @Getter Date created_at;
}
