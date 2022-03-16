package student.laurens.novibackend.users;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Integer id;

    @Column(name = "ROLE_NAME")
    private @Getter String name;

}
